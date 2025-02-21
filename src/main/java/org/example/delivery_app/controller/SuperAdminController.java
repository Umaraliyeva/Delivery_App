package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.dto.UserDTO;
import org.example.delivery_app.entity.Attachment;
import org.example.delivery_app.entity.AttachmentContent;
import org.example.delivery_app.entity.Role;
import org.example.delivery_app.entity.User;
import org.example.delivery_app.repo.AttachmentContentRepository;
import org.example.delivery_app.repo.AttachmentRepository;
import org.example.delivery_app.repo.RoleRepository;
import org.example.delivery_app.repo.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/superAdmin")
@RequiredArgsConstructor
public class SuperAdminController {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;


    @GetMapping()
    public HttpEntity<?> getUser(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(
            @RequestParam("id") Integer userId,
            @RequestParam("roles") List<Integer> roleIds,
            @RequestParam("fullName") String fullName) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        user.setFullName(fullName);
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }


    @GetMapping("/user/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public HttpEntity<?> updateUser(@PathVariable Integer id,
                                    @ModelAttribute UserDTO userDTO,
                                    @RequestParam("file") MultipartFile file) throws IOException {

        // Foydalanuvchini bazadan topamiz
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Faylni saqlash (Agar fayl mavjud bo'lsa)
        if (!file.isEmpty()) {
            Attachment attachment = Attachment.builder()
                    .fileName(file.getOriginalFilename())
                    .build();
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = AttachmentContent.builder()
                    .content(file.getBytes())
                    .attachment(attachment)
                    .build();
            attachmentContentRepository.save(attachmentContent);

            user.setAttachment(attachment);
        }

        // Foydalanuvchi ma'lumotlarini yangilash
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setRoles(roleRepository.findByIdIn(userDTO.getRoleIds()));

        // Parolni o'zgartirish (faqat agar berilgan bo'lsa)
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        // Yangilangan foydalanuvchini saqlash
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestPart("userDTO") UserDTO userDTO,
            @RequestPart("file") MultipartFile file) throws IOException {

        List<Role> roles = roleRepository.findByIdIn(userDTO.getRoleIds());
            Attachment attachment = Attachment.builder()
                    .fileName(file.getOriginalFilename())
                    .build();
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = AttachmentContent.builder()
                    .content(file.getBytes())
                    .attachment(attachment)
                    .build();
            attachmentContentRepository.save(attachmentContent);
        User user = User.builder()
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .roles(roles)
                .attachment(attachment)
                .build();

        userRepository.save(user);

        return ResponseEntity.status(201).body(user);
    }

    @DeleteMapping("/user/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRoles(new ArrayList<>());
        userRepository.save(user);
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }
}


