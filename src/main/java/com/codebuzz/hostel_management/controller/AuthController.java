package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.AuthResponse;
import com.codebuzz.hostel_management.model.User;
import com.codebuzz.hostel_management.model.UserRequest;
import com.codebuzz.hostel_management.repository.UserRepository;
import com.codebuzz.hostel_management.security.AuthRequest;
import com.codebuzz.hostel_management.security.JwtUtil;
import com.codebuzz.hostel_management.service.AuthenticationService;
import com.codebuzz.hostel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        logger.info("Registering user: {}", userRequest.getUsername());
        User newUser = userService.createUser(userRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        logger.info("Logging in user: {}", authRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(new Authentication() {
            });
            String token = jwtUtil.generateToken(authentication);
            logger.info("JWT token generated for user: {}", authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, jwtUtil.getExpirationDate(token)));



        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for user: {}", authRequest.getUsername());
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (LockedException e) {
            logger.warn("User account is locked: {}", authRequest.getUsername());
            return ResponseEntity.status(403).body("Account is locked");
        } catch (DisabledException e) {
            logger.warn("User account is disabled: {}", authRequest.getUsername());
            return ResponseEntity.status(403).body("Account is disabled");
        } catch (Exception e) {
            logger.error("Unexpected error during login for user: {}", authRequest.getUsername(), e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}