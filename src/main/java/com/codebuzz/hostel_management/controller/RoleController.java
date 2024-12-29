package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.codebuzz.hostel_management.model.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }
}
