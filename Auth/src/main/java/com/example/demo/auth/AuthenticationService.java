package com.example.demo.auth;

import com.example.demo.exception.UserException;
import com.example.demo.config.JwtService;

import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.token.TokenType;

import com.example.demo.token.*;
import com.example.demo.user.Role;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Key;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Integer refreshExpiration;
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    private final PasswordResetTokenRepository passwordResetTokenRepository;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws UserException {


        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        saveUserToken(savedUser, jwtToken);


        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(savedUser.getFirstname())
                .lastname(savedUser.getLastname())
                .build();


    }

//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//
//
//
//
//
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//
//        );
////        now user is authenticated
////        now we generate token to be sent
//        var user = repository.findByEmail(request.getEmail()).orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
////        saving the generated token
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
//
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken).build();
//
//    }

    @Transactional
    public AuthenticationResponse authenticate(HttpServletResponse response, AuthenticationRequest request) throws UserException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Authentication successful, continue with token generation
            var user = repository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
//            revokeAllUserTokens(user);
            updateUserToken(user, jwtToken);
            creatCookie(response, refreshToken, refreshExpiration / 1000);
            return AuthenticationResponse.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .accessToken(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Authentication failed, handle the exception
            throw new UserException("Authentication failed: " + e.getMessage());
        }
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    revoke all users
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
           token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }


    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void updateUserToken(User user, String jwtToken) {
        var storedToken = tokenRepository.findByUser_Id(user.getId()).orElse(null);
        if (storedToken != null) {
            storedToken.setToken(jwtToken);
            storedToken.setExpired(false);
            storedToken.setRevoked(false);
            tokenRepository.save(storedToken);
        }
    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        // here we firstly, doing extracting the request (header,jwt,useremail)
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//
//
//        //userEmail is in the jwt token , so we need to extract it from the jwt token, in order to do that we need a class called jwtService to manipulate the jwt token
//        userEmail = jwtService.extractUsername(refreshToken);
//
////        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not
//
//
//        if (userEmail != null) {
//
//
//            var user = this.repository.findByEmail(userEmail).orElseThrow();
//
//            if (jwtService.isTokenValid(refreshToken, user)) {
//               var accessToken = jwtService.generateToken(user);
//               revokeAllUserTokens(user);
//               saveUserToken(user, accessToken);
//               var authResponse = AuthenticationResponse.builder()
//                       .accessToken(accessToken)
//                       .refreshToken(refreshToken)
//                       .build();
//               new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//        }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = null;
        final String email;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("rf token "+refreshToken);
        if (refreshToken == null) {
            return;
        }
        email = jwtService.extractUsername(refreshToken);
//        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not


        if (email != null) {
            var user = this.repository.findByEmail(email).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                System.out.println("new token "+accessToken);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
                updateUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }
//    public AuthenticationResponse refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        // here we firstly, doing extracting the request (header,jwt,useremail)
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new IOException("Refresh token is missing");
//        }
//        refreshToken = authHeader.substring(7);
//
//
//        //userEmail is in the jwt token , so we need to extract it from the jwt token, in order to do that we need a class called jwtService to manipulate the jwt token
//        userEmail = jwtService.extractUsername(refreshToken);
//
////        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not
//
//
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail).orElseThrow();
//
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                return AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//
//            }
//        }
//        return null;
//    }

    private void creatCookie(HttpServletResponse response, String refreshToken, Integer MaxAge) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);  // Make the cookie accessible only through HTTP
        refreshTokenCookie.setMaxAge(MaxAge);  // Set the cookie's expiration time in seconds
        refreshTokenCookie.setPath("/");  // Set the cookie's path to the root
        response.addCookie(refreshTokenCookie);
    }



}
