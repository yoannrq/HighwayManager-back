package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.UserCreationDTO;
import com.example.HighwayManager.dto.UserDTO;
import com.example.HighwayManager.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.HighwayManager.service.UserService;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.model.Role;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_WithNewEmail_ShouldCreateUser() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        UserCreationDTO userBody = new UserCreationDTO();
        userBody.setEmail("nouveau@email.com");
        userBody.setPassword("motdepasse");
        userBody.setFirstname("John");
        userBody.setLastname("Doe");
        userBody.setRoleId(1L);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("nouveau@email.com");
        savedUser.setPassword("motdepasseHashé");
        savedUser.setFirstname("John");
        savedUser.setLastname("Doe");
        savedUser.setRole(role);

        when(userService.getUserByEmail("nouveau@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("motdepasse")).thenReturn("motdepasseHashé");
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);
        when(roleService.getRoleById(1L)).thenReturn(Optional.of(role));

        // Act
        UserDTO result = userController.createUser(userBody);

        // Assert
        assertNotNull(result);
        assertEquals("nouveau@email.com", result.getEmail());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals(1L, result.getId());
        assertNull(result.getPassword());
        assertEquals("USER", result.getRoleName());
        verify(passwordEncoder).encode("motdepasse");
        verify(userService).saveUser(any(User.class));
        verify(roleService).getRoleById(1L);
    }

    @Test
    void createUser_WithExistingEmail_ShouldThrowException() {
        // Arrange
        UserCreationDTO user = new UserCreationDTO();
        user.setEmail("existant@email.com");

        when(userService.getUserByEmail("existant@email.com")).thenReturn(Optional.of(new User()));

        // Act & Assert
        Exception exception = assertThrows(com.example.HighwayManager.exception.IllegalStateException.class, () -> userController.createUser(user));
        assertEquals("Cet email est déjà utilisé", exception.getMessage());
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
