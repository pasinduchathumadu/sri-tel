package com.middleware.authservice.controller;

import com.middleware.authservice.dto.AuthRequest;
import com.middleware.authservice.model.UserCredential;
import com.middleware.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService service;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @GetMapping
    public String test() {
        return "Test";
    }

    @GetMapping
    public String Test(){
        return "Hello";
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        System.out.println("Haaaaa------------------");
        return service.saveUser(user);
    }

//    @PostMapping("/token")
//    public String getToken(@RequestBody AuthRequest authRequest) {
//        System.out.println("Heeeee---------------");
//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authenticate.isAuthenticated()) {
//            return service.generateToken(authRequest.getUsername());
//        } else {
//            throw new RuntimeException("invalid access");
//        }
//    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
