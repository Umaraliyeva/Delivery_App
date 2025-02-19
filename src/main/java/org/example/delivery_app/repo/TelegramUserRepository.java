package org.example.delivery_app.repo;

import org.example.delivery_app.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Integer> {
}