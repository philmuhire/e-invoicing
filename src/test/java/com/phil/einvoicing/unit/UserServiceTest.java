package com.phil.einvoicing;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.phil.einvoicing.user.User;
import com.phil.einvoicing.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

//    public User getUserById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }



    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_UserExists() {
        // Arrange
        int userId = 1;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setNames("John Doe");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = userRepository.findById(userId);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.get().getNames());
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        // Arrange
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepository.findById(userId);

        // Assert
        assertNull(result);
    }
}
