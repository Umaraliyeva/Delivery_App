package org.example.delivery_app.repo;

import org.example.delivery_app.entity.Role;
import org.example.delivery_app.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
    List<Role> findByIdIn(List<Integer> ids);
}