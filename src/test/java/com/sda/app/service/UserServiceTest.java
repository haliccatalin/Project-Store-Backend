package com.sda.app.service;

import com.sda.app.entity.User;
import com.sda.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        // Mock data
        List<User> userList = Arrays.asList(
                user1, user2
        );

        // Define the behavior of the repository mock
        when(userRepository.findAll()).thenReturn(userList);

        // Call the service method
        List<User> result = userService.findAll();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        // Mock data
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        // Define the behavior of the repository mock
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        // Call the service method
        Optional<User> result = userService.findById(1);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
    }

    @Test
    public void testSaveUser() {
        // Mock data
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        // Call the service method
        userService.createUser(user1);

        // Verify that the repository's save method was called
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void testDeleteUser() {
        // Call the service method
        userService.deleteById(1);

        // Verify that the repository's deleteById method was called
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetUserByFullName() {
        // Mock data
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");


        // Define the behavior of the repository mock
        when(userRepository.getByUsername("user1")).thenReturn(Optional.of(user1));

        // Call the service method
        Optional<User> result = userService.findByUsername("user1");

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
    }

    @Test
    public void testGetUserByNonexistentFullName() {
        // Define the behavior of the repository mock
        when(userRepository.getByUsername("Nonexistent User")).thenReturn(Optional.empty());

        // Call the service method
        Optional<User> result = userService.findByUsername("Nonexistent User");

        // Verify the result
        assertFalse(result.isPresent());
    }
}
