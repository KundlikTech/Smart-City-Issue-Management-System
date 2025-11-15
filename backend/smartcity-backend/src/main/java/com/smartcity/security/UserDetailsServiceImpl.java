package com.smartcity.security;

import com.smartcity.model.User;
import com.smartcity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 🔥 IMPORTANT: tell Spring Security password is plain text
        return User.builder()
                .username(user.getEmail())
                .password("{noop}" + user.getPassword()) // <-- FIXED!!
                .roles(user.getRole())
                .build();
    }
}