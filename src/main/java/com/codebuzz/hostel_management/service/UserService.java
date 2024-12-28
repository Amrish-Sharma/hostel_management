package com.codebuzz.hostel_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createUser(UserRequest userRequest) {
        // Fetch role from the database
        Role role = roleRepository.findByName(userRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userRequest.getRole()));

        // Create new user
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // Make sure to encode this in production
        user.setRoles(Collections.singleton(role)); // Assign the role

        return userRepository.save(user);
    }
}
