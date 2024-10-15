package com.example.HighwayManager.service;

import java.util.Optional;

import com.example.HighwayManager.model.User;
import com.example.HighwayManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(final long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }
}
