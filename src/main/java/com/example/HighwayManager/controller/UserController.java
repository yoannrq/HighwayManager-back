package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.UserCreationDTO;
import com.example.HighwayManager.dto.UserDTO;
import com.example.HighwayManager.model.Role;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.RoleService;
import com.example.HighwayManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.HighwayManager.exception.IllegalStateException;
import com.example.HighwayManager.exception.IllegalArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    /**
     * Create - Add a new user
     * @param userBody as an object user
     * @return The user object saved
     * @throws IllegalStateException if email is already used or role not found
     * @throws IllegalArgumentException if role is not found
     */
    @PostMapping("/user")
    public UserDTO createUser(@RequestBody UserCreationDTO userBody) {
        if (userService.getUserByEmail(userBody.getEmail()).isPresent()) {
            throw new IllegalStateException("Cet email est déjà utilisé");
        }

        User newUser = new User();
        newUser.setFirstname(userBody.getFirstname());
        newUser.setLastname(userBody.getLastname());
        newUser.setEmail(userBody.getEmail());
        newUser.setPassword(passwordEncoder.encode(userBody.getPassword()));

        Role role = roleService.getRoleById(userBody.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));
        newUser.setRole(role);


        User savedUser = userService.saveUser(newUser);
        return new UserDTO(savedUser);
    }


    /**
     * Read - Get one user
     * @param id The id of the user
     * @return user
     * @throws IllegalArgumentException if user is not found
     */
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable final long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDTO(user);
        } else {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }
    }

    /**
     * Read - Get all users
     * @return - List of users
     */
    @GetMapping("/user")
    public List<UserDTO> getUsers() {
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
     * @return UserDTO - The updated user as a DTO
     * @throws IllegalArgumentException if user is not found
     * @throws IllegalStateException if email is already used
     */
    @PatchMapping("/user/{id}")
    public UserDTO updateUser(@PathVariable final long id, @RequestBody UserCreationDTO userBody) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur introuvable");
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

        if (userBody.getRoleId() != null) {
            Role role = roleService.getRoleById(userBody.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));
            userToUpdate.setRole(role);
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
