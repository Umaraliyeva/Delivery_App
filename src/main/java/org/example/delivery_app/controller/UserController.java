package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @GetMapping()
    public HttpEntity<?> getUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        System.out.println("User object: " + user); // Debug qilish

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("fullname", user.getFullName());
        userData.put("attachmentId", user.getAttachment() != null ? user.getAttachment().getId() : null);
        userData.put("roles", user.getRoles());

        return ResponseEntity.ok(userData);
    }

    @GetMapping("/admin")
    public HttpEntity<?> getUserAdmin(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        System.out.println(user.getRoles().toString());
        // Check for "admin" role
        if (user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body("Forbidden: Not an admin");
        }

        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", user.getId());
        user1.put("fullname", user.getFullName());
        user1.put("attachmentId", user.getAttachment().getId());
        user1.put("roles", user.getRoles());

        return ResponseEntity.ok(user1);
    }
}
