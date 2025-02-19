package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.repo.RoleRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping
    public HttpEntity<?> getRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
