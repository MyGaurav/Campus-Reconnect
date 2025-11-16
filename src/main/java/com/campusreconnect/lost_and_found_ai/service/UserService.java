package com.campusreconnect.lost_and_found_ai.service;

import com.campusreconnect.lost_and_found_ai.entity.User;
import com.campusreconnect.lost_and_found_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged in user not found in database"));
    }

    public void register(User user) {
        System.out.println("User details: " + user);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("Saved successfully!");
    }

}
