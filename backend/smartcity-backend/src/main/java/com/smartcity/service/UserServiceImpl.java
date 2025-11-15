package com.smartcity.service;

import com.smartcity.model.User;
import com.smartcity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // Store password exactly as received
        System.out.println("WARNING: Storing plaintext password: " + user.getPassword());

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
