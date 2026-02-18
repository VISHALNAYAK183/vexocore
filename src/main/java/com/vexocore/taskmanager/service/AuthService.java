package com.vexocore.taskmanager.service;

import com.vexocore.taskmanager.dto.LoginRequest;
import com.vexocore.taskmanager.dto.LoginResponse;
import com.vexocore.taskmanager.dto.SignupRequest;
import com.vexocore.taskmanager.entity.User;
import com.vexocore.taskmanager.repository.UserRepository;
import com.vexocore.taskmanager.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    public String signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Error: Email already registered!";
        }

        LocalDate dob = request.getDob();
        int age = Period.between(dob, LocalDate.now()).getYears();

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                age,
                request.getDob(),
                request.getGender()
        );

        userRepository.save(user);
        return "User registered successfully!";
    }

   
    public Object login(LoginRequest request) {

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

        return new LoginResponse(token);
    }
}
