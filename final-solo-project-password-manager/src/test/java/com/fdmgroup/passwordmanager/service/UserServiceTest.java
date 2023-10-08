package com.fdmgroup.passwordmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepo;
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	private User user;

	@BeforeEach
	void setUp() {
		this.user = new User();
	}

	@Test
	void testFindById_ShouldReturnUserId() {
		user.setId(1);
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		Optional<User> result = userService.findById(1);
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
	}

	@Test
	void testFindById_ShouldReturnNoUser_WhenNoUserFound() {
		when(userRepo.findById(anyInt())).thenReturn(Optional.empty());
		Optional<User> result = userService.findById(1);
		assertFalse(result.isPresent());
		verify(userRepo, times(1)).findById(1);
	}

	@Test
	void testFindByEmail_ShouldReturnUserEmail() {
		user.setEmail("test@example.com");
		when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
		Optional<User> result = userService.findByEmail("test@example.com");
		assertTrue(result.isPresent());
		assertEquals("test@example.com", result.get().getEmail());
	}

	@Test
	void testFindByEmail_ShouldReturnNoUser_WhenNoUserFound() {
		when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
		Optional<User> result = userService.findByEmail("test@email.com");
		assertFalse(result.isPresent());
		verify(userRepo, times(1)).findByEmail("test@email.com");
	}

	@Test
	void testFindAll_ShouldReturnAllUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		when(userRepo.findAll()).thenReturn(users);
		List<User> result = userService.findAll();
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void testFindByHashedPassword_EmptyString() {
		when(userRepo.findByHashedPassword("")).thenReturn(Optional.empty());
		Optional<User> result = userService.findByHashedPassword("");
		assertFalse(result.isPresent());
	}

	@Test
	void testFindByHashedPassword_Null() {
		when(userRepo.findByHashedPassword(null)).thenReturn(Optional.empty());
		Optional<User> result = userService.findByHashedPassword(null);
		assertFalse(result.isPresent());
	}

	@Test
	void testAddUser() {
		when(userRepo.save(user)).thenReturn(user);
		User result = userService.addUser(user);
		assertNotNull(result);
	}

	@Test
	void testAddUser_Null() {
		when(userRepo.save(null)).thenThrow(new IllegalArgumentException());
		assertThrows(IllegalArgumentException.class, () -> userService.addUser(null));
	}

	@Test
	void testUpdateById_UserExists() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
		UserService userService = new UserService(userRepo, passwordEncoder);
		when(passwordEncoder.encode(ArgumentMatchers.any())).thenAnswer(i -> i.getArguments()[0]);

		User existingUser = new User();
		existingUser.setId(1);
		existingUser.setEmail("a@gmail.com");
		existingUser.setHashedPassword("123456");

		User updatedUser = new User();
		updatedUser.setEmail("b@gmail.com");
		updatedUser.setHashedPassword("12345678");

		when(userRepo.existsById(1)).thenReturn(true);
		when(userRepo.save(updatedUser)).thenReturn(updatedUser);

		User result = userService.updateById(1, updatedUser);
		assertEquals("b@gmail.com", result.getEmail());
	}

	@Test
	public void testUpdateById_UserDoesNotExist() {
		User newUser = new User();
		newUser.setEmail("Jane@gmail.com");
		when(userRepo.existsById(1)).thenReturn(false);
		assertThrows(CustomizedNotFoundException.class, () -> userService.updateById(1, newUser));
	}

	@Test
	void testDeleteById_ShouldDeleteUserByID() {
		User existingUser = new User();
		existingUser.setId(1);
		existingUser.setEmail("a@gmail.com");
		when(userRepo.existsById(1)).thenReturn(true);
		userService.deleteById(1);
		verify(userRepo, times(1)).deleteById(1);
	}

	@Test
	void testDeleteById_ShouldThrowCustomizedNotFoundException_WhenNotFound() {
		assertThrows(CustomizedNotFoundException.class, () -> userService.deleteById(999));
	}
}