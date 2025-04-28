package org.example.sutalkbe.service;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.User;
import org.example.sutalkbe.exception.ResourceNotFoundException;
import org.example.sutalkbe.repository.PostRepository;
import org.example.sutalkbe.repository.UserRepository;
import org.springframework.stereotype.Service;

// UserService.java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return (User) userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

