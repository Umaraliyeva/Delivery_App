package org.example.delivery_app.controller;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.example.delivery_app.dto.LoginDTO;
import org.example.delivery_app.entity.Attachment;
import org.example.delivery_app.entity.AttachmentContent;
import org.example.delivery_app.entity.Role;
import org.example.delivery_app.entity.User;
import org.example.delivery_app.enums.RoleName;
import org.example.delivery_app.repo.AttachmentContentRepository;
import org.example.delivery_app.repo.AttachmentRepository;
import org.example.delivery_app.repo.RoleRepository;
import org.example.delivery_app.repo.UserRepository;
import org.example.delivery_app.service.TokenService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@MultipartConfig

public class AuthController {


    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("fullName") String fullName,
            @RequestParam("password") String password,
            @RequestParam("file") MultipartFile file) throws IOException {

        Attachment attachment = Attachment.builder()
                .fileName(file.getOriginalFilename())
                .build();
        attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = AttachmentContent.builder()
                .content(file.getBytes())
                .attachment(attachment)
                .build();
        attachmentContentRepository.save(attachmentContent);

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow();
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .attachment(attachment)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        return ResponseEntity.ok(tokenService.generateToken((User)authenticate.getPrincipal()));

    }



}
