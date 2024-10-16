package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.UserDTO;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create - Add a new user
     * @param userBody as an object user
     * @return The user object saved
     * @throws IllegalStateException if email is already used
     */
    @PostMapping("/user")
    public UserDTO createUser(@RequestBody User userBody) {
        if (userService.getUserByEmail(userBody.getEmail()).isPresent()) {
            throw new IllegalStateException("Cet email est déjà utilisé");
        }

        userBody.setPassword(passwordEncoder.encode(userBody.getPassword()));

        User savedUser = userService.saveUser(userBody);
        return new UserDTO(savedUser);
    }

    /**
     * Read - Get one user
     * @param id The id of the user
     * @return user || null
     */
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable final long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDTO(user);
        } else {
            return null;
        }
    }

    /**
     * Read - Get all users
     * @return - List of users
     */
    @GetMapping("/user")
    public Iterable<UserDTO> getUsers() {
        Iterable<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    /**
     * Patch - Update an existing user
     * @param id - The id of the user to update
     * @param userBody - The User object containing updated fields
     * @return UserDTO || null - The updated user as a DTO
     * @throws IllegalStateException if email is already used
     */
    @PatchMapping("/user/{id}")
    public UserDTO updateUser(@PathVariable final long id, @RequestBody User userBody) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User userToUpdate = optionalUser.get();

        if (userBody.getFirstname() != null && !userBody.getFirstname().isEmpty()) {
            userToUpdate.setFirstname(userBody.getFirstname());
        }

        if (userBody.getLastname() != null && !userBody.getLastname().isEmpty()) {
            userToUpdate.setLastname(userBody.getLastname());
        }

        if (userBody.getEmail() != null && !userBody.getEmail().isEmpty()) {
            if (!userToUpdate.getEmail().equals(userBody.getEmail())) {
                if (userService.getUserByEmail(userBody.getEmail()).isPresent()) {
                    throw new IllegalStateException("Cet email est déjà utilisé");
                }
                userToUpdate.setEmail(userBody.getEmail());
            }
        }

        if (userBody.getPassword() != null && !userBody.getPassword().isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(userBody.getPassword()));
        }

        User savedUser = userService.saveUser(userToUpdate);
        return new UserDTO(savedUser);
    }

    /**
     * Delete - Delete an user
     * @param id - The id of the user to delete
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable final long id) {
        userService.deleteUser(id);
    }
}
