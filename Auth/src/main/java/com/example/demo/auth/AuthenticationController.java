package com.example.demo.auth;

import com.example.demo.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService service;



    public AuthenticationController(AuthenticationService service) {
        this.service = service;

    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody  RegisterRequest request
    ) throws UserException {

        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        System.out.println("I am in auth service --------------------------------------------------------");
        service.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(HttpServletResponse httpresponse, @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(httpresponse, request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException | UserException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(e.getMessage()));
        }
    }

    @GetMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        System.out.println("refresh token");
        service.refreshToken(request,response);
    }


}
