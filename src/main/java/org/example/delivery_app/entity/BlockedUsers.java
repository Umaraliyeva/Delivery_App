package org.example.delivery_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockedUsers {
    @Id
    private String email;
    private LocalDateTime blockedAt;
}
