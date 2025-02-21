package org.example.delivery_app.config;

import org.example.delivery_app.entity.Role;
import org.example.delivery_app.entity.User;
import org.example.delivery_app.enums.RoleName;
import org.example.delivery_app.repo.RoleRepository;
import org.example.delivery_app.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class Dataloader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Dataloader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role = Role.builder()
                .roleName(RoleName.ROLE_SUPER_ADMIN)
                .build();
        List<Role> allRoles = roleRepository.saveAll(new ArrayList<>(List.of(role)));

        User user = User.builder()
                .roles(new ArrayList<>(List.of(allRoles.get(0))))
                .username("jetbrains085@gmail.com")
                .password(passwordEncoder.encode("123"))
                .fullName("Eshmat Toshmatov")
                .build();
        userRepository.save(user);

    }
}
