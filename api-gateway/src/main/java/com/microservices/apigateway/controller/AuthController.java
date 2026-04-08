package com.microservices.apigateway.controller;


import com.microservices.apigateway.Util.JwtUtil;
import com.microservices.apigateway.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    //  Login API
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        //  Dummy validation (replace with DB later)
        if ("admin".equals(request.getUsername()) &&
                "password".equals(request.getPassword())) {

            return jwtUtil.generateToken(request.getUsername());
        }

        throw new RuntimeException("Invalid credentials");
    }

    // Test endpoint (optional)
    @GetMapping("/test")
    public String test() {
        return "Auth service working";
    }
}