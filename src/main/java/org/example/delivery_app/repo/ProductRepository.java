package org.example.delivery_app.repo;

import org.example.delivery_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(int categoryId);
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
}