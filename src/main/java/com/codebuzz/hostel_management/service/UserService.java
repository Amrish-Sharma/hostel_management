package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.User;
import com.codebuzz.hostel_management.model.Role;

import com.codebuzz.hostel_management.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codebuzz.hostel_management.repository.UserRepository;
import com.codebuzz.hostel_management.repository.RoleRepository;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        Role role = roleRepository.findByName(userRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userRequest.getRole()));

        // Create a new User entity
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // Ensure password is encoded

        // Assign the role
        user.setRoles(Collections.singleton(role));

        // Save the user
        return userRepository.save(user);
    }
}
