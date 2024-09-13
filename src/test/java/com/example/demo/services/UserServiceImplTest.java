package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.ERole;
import com.example.demo.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = User.builder()
                .userId(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(ERole.USER)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        User user1 = User.builder()
                .userId(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(ERole.USER)
                .build();

        User user2 = User.builder()
                .userId(2L)
                .username("jane_doe")
                .email("jane.doe@example.com")
                .password("password456")
                .role(ERole.ADMIN)
                .build();

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("jane_doe", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        User user = User.builder()
                .userId(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(ERole.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        assertEquals(ERole.USER, result.get().getRole());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User existingUser = User.builder()
                .userId(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(ERole.USER)
                .build();

        User updatedUser = User.builder()
                .userId(1L)
                .username("updated_john")
                .email("updated.john@example.com")
                .password("newpassword")
                .role(ERole.ADMIN)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("updated_john", result.getUsername());
        assertEquals("ADMIN", result.getRole().name());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteAllUsers() {
        doNothing().when(userRepository).deleteAll();

        userService.deleteAllUsers();

        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void testUserDetailsMethods() {
        User user = User.builder()
                .userId(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(ERole.USER)
                .build();

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());

        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(ERole.USER.name())));
    }
}
