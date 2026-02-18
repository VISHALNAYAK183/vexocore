package com.vexocore.taskmanager.controller;

import com.vexocore.taskmanager.dto.*;
import com.vexocore.taskmanager.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
