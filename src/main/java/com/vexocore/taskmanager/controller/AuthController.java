package com.vexocore.taskmanager.controller;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import com.vexocore.taskmanager.dto.*;
import com.vexocore.taskmanager.entity.User;
import com.vexocore.taskmanager.repository.UserRepository;
import com.vexocore.taskmanager.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Signup
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Error: Email already registered!";
        }
  LocalDate dob = request.getDob();
        int age = Period.between(dob, LocalDate.now()).getYears();
        User user = new User(
                request.getId(),
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), // password encrypted
                age,
                request.getDob(),
                request.getGender()
        );

        userRepository.save(user);
        return "User registered successfully!";
    }

 
    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return "Error: Invalid email or password!";
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Error: Invalid email or password!";
        }

       String token = jwtUtil.generateToken(
    user.getEmail(),
    user.getName(),
    user.getId().toString(),
    user.getAge().toString(),
    user.getDob().toString(),
    user.getGender()
);
        System.out.println("Tokesn"+token);
          String[] parts = token.split("\\.");
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        System.out.println("Payload: " + payload);
        return new LoginResponse(token);
    }
}
