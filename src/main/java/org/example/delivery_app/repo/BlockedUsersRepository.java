package org.example.delivery_app.repo;

import org.example.delivery_app.entity.BlockedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUsersRepository extends JpaRepository<BlockedUsers, String> {
  }