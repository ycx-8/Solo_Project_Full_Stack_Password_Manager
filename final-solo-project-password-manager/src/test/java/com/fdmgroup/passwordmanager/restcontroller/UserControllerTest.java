package com.fdmgroup.passwordmanager.restcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.passwordmanager.model.User;
import com.fdmgroup.passwordmanager.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setId(1);
        user1.setEmail("user1@gmail.com");

        user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@gmail.com");
    }
    
    @Test
    void testFindById() {
        when(userService.findById(1)).thenReturn(Optional.of(user1));
        User foundUser = userController.findByUserId(1);
        assertEquals(user1, foundUser);
    }

    @Test
    void testFindAll() {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userController.getUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void testFindByEmail() {
        when(userService.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user1));
        User foundUser = userController.findByEmail("user1@gmail.com");
        assertEquals(user1, foundUser);
    }

    @Test
    void testFindByHashedPassword() {
        when(userService.findByHashedPassword("hashed_password")).thenReturn(Optional.of(user1));
        User foundUser = userController.findByHashedPassword("hashed_password");
        assertEquals(user1, foundUser);
    }

    @Test
    void testaddUser() {
        when(userService.registerUser(any(User.class))).thenReturn(user1);
        User newUser = userController.addUser(new User());
        assertEquals(user1, newUser);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateById(eq(1), any(User.class))).thenReturn(user1);
        User updatedUser = userController.updateUser(1, new User());
        assertEquals(user1, updatedUser);
    }

    @Test
    void testDeleteUser() {
        when(userService.findById(1)).thenReturn(Optional.of(user1));
        String response = userController.deleteUser(1);
        assertEquals("Deleted user id: 1", response);
    }
}

