package com.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcity.model.User;
import java.util.Optional;

/**
 * Repository interface for managing User entity.
 * Provides built-in CRUD operations and custom queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find user by email
    Optional<User> findByEmail(String email);
}
