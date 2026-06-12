package com.taskapp.service;

import com.taskapp.model.User;
import com.taskapp.repository.UserRepository;
import com.taskapp.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public String register(String username, String password) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username already taken");
        userRepository.save(new User(username, passwordEncoder.encode(password)));
        return jwtUtil.generateToken(username);
    }

    public String login(String username, String password) {
        if (!userRepository.existsByUsername(username))
            throw new RuntimeException("User not found");
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            return jwtUtil.generateToken(auth.getName());
        } catch (Exception e) {
            throw new RuntimeException("Incorrect password");
        }
    }
}
