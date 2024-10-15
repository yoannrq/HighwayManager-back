package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
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
     * @param user as an object user
     * @return The user object saved
     * @throws IllegalStateException if email is already used
     */
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        //Verify if email is not already used in database
        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Cet email est déjà utilisé");
        }

        // Password hached before saving user
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userService.saveUser(user);
    }

    /**
     * Read - Get one user
     * @param id The id of the user
     * @return user || null
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable final long id) {
        Optional<User> user = userService.getUserById(id);
        return user.orElse(null);
    }

    /**
     * Read - Get all users
     * @return - An Iterable object of Users
     */
    @GetMapping("/user")
    public Iterable<User> getUsers() {
        return userService.getAllUsers();
    }

    /**
     * Patch - Update an existing user
     * @param id - The id of the user to update
     * @param userBody - The user object to update
     * @return user || null - The user object updated
     * @throws IllegalStateException if email is already used
     */
    @PatchMapping("/user/{id}")
    public User updateUser(@PathVariable final long id, @RequestBody User userBody) {
        Optional<User> userInDatabase = userService.getUserById(id);
        if (userInDatabase.isPresent()) {
            User userToUpdate = userInDatabase.get();

            String firstname = userBody.getFirstname();
            if (firstname != null && !firstname.isEmpty()) {
                userToUpdate.setFirstname(firstname);
            }

            String lastname = userBody.getLastname();
            if (lastname != null && !lastname.isEmpty()) {
                userToUpdate.setLastname(lastname);
            }

            String email = userBody.getEmail();
            if (email != null && !email.isEmpty()) {
            //Verify if email is not already used in database
            Optional<User> isEmailAlreadyUsed = userService.getUserByEmail(email);
                if (isEmailAlreadyUsed.isEmpty()) {
                    userToUpdate.setEmail(email);
                } else {
                    throw new IllegalStateException("Cet email est déjà utilisé");
                }
            }

            String password = userBody.getPassword();
            if (password != null && !password.isEmpty()) {
                // Password hached before insertion
                String hashedPassword = passwordEncoder.encode(password);
                userToUpdate.setPassword(hashedPassword);
            }

            return userService.saveUser(userToUpdate);
        } else {
            return null;
        }
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
