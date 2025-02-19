package org.example.delivery_app.repo;

import org.example.delivery_app.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}