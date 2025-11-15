package com.smartcity.service;

import com.smartcity.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for User-related operations.
 */
public interface UserService {

    /**
     * Register a new user (saves the user after hashing password).
     * @param user user to register
     * @return saved user
     */
    User register(User user);

    /**
     * Find user by email.
     * @param email email address
     * @return optional user
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by id.
     * @param id user id
     * @return optional user
     */
    Optional<User> findById(Long id);

    /**
     * Get all users (admins + citizens).
     * @return list of users
     */
    List<User> findAll();
}
