package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.HighwayManager.service.UserService;
import com.example.HighwayManager.model.User;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_WithNewEmail_ShouldCreateUser() {
        // Arrange
        User userBody = new User();
        userBody.setEmail("nouveau@email.com");
        userBody.setPassword("motdepasse");
        userBody.setFirstname("John");
        userBody.setLastname("Doe");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("nouveau@email.com");
        savedUser.setPassword("motdepasseHashé");
        savedUser.setFirstname("John");
        savedUser.setLastname("Doe");

        when(userService.getUserByEmail("nouveau@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("motdepasse")).thenReturn("motdepasseHashé");
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO result = userController.createUser(userBody);

        // Assert
        assertNotNull(result);
        assertEquals("nouveau@email.com", result.getEmail());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals(1L, result.getId());
        assertNull(result.getPassword()); // Le DTO ne devrait pas contenir le mot de passe
        verify(passwordEncoder).encode("motdepasse");
        verify(userService).saveUser(any(User.class));
    }

    @Test
    void createUser_WithExistingEmail_ShouldThrowException() {
        // Arrange
        User user = new User();
        user.setEmail("existant@email.com");

        when(userService.getUserByEmail("existant@email.com")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> userController.createUser(user));
    }

    @Test
    void getUserById_WithExistingId_ShouldReturnUserDTO() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDTO result = userController.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@example.com", result.getEmail());
        assertNull(result.getPassword());
    }

}
