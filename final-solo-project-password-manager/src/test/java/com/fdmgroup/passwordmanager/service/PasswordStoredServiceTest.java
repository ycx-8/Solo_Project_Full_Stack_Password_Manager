package com.fdmgroup.passwordmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.*;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;

@ExtendWith(MockitoExtension.class)
class PasswordStoredServiceTest {

	@Mock
	private PasswordStoredRepository passRepoMock;
	@Mock
	private UserRepository userRepoMock;
	@Mock
	private SecurityContextHolder secContextMock;
	@Mock
	private Authentication auth;

	@InjectMocks
	private PasswordStoredService passService;

	private PasswordStored password;
	private User user;

	@BeforeEach
	void setUp() {
		this.password = new PasswordStored();
		this.password.setId(1);
		this.password.setServiceName("Amazon");
		this.password.setUsername("john_doe");
		this.password.setPassword("password");
		this.user = new User();
		user.setEmail("a@gmail.com");
		user.setId(1);
	}

	@Test
	void testFindById() {
		when(passRepoMock.findById(1)).thenReturn(Optional.of(password));
		Optional<PasswordStored> foundPassword = passService.findById(1);
		assertTrue(foundPassword.isPresent());
		assertEquals(password, foundPassword.get());
	}

	@Test
	void testFindByIdNotFound() {
		when(passRepoMock.findById(1)).thenReturn(Optional.empty());
		Optional<PasswordStored> foundPassword = passService.findById(1);
		assertFalse(foundPassword.isPresent());
	}

	@Test
	void testFindByUsername() {
		when(passRepoMock.findByUsername("john_doe")).thenReturn(Optional.of(password));
		Optional<PasswordStored> foundPassword = passService.findByUsername("john_doe");
		assertTrue(foundPassword.isPresent());
		assertEquals(password, foundPassword.get());
	}

	@Test
	void testFindByUsernameNotFound() {
		when(passRepoMock.findByUsername("john_doe")).thenReturn(Optional.empty());
		Optional<PasswordStored> foundPassword = passService.findByUsername("john_doe");
		assertFalse(foundPassword.isPresent());
	}

	@Test
	void testFindByUserIdAndServiceName() {
		when(passRepoMock.findByUserIdAndServiceName(1, "Amazon")).thenReturn(Optional.of(password));
		Optional<PasswordStored> foundPassword = passService.findByUserIdAndServiceName(1, "Amazon");
		assertTrue(foundPassword.isPresent());
		assertEquals(password, foundPassword.get());
	}

	@Test
	void testFindByUserIdAndServiceNameNotFound() {
		when(passRepoMock.findByUserIdAndServiceName(1, "Amazon")).thenReturn(Optional.empty());
		Optional<PasswordStored> foundPassword = passService.findByUserIdAndServiceName(1, "Amazon");
		assertFalse(foundPassword.isPresent());
	}

	@Test
	void testFindAll() {
		PasswordStored password2 = new PasswordStored(2, "Google", "jane_doe", "password", null);
		when(passRepoMock.findAll()).thenReturn(Arrays.asList(password, password2));
		List<PasswordStored> allPasswords = passService.findAll();
		assertEquals(2, allPasswords.size());
	}

	@Test
	void testAddPassword() {
		try (MockedStatic<SecurityContextHolder> mockHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
	        SecurityContext securityContextMock = mock(SecurityContext.class);
	        Authentication authenticationMock = mock(Authentication.class);
	        mockHolder.when(SecurityContextHolder::getContext).thenReturn(securityContextMock);
	        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
	        when(authenticationMock.getName()).thenReturn("a@gmail.com");
	        when(userRepoMock.findByEmail("a@gmail.com")).thenReturn(Optional.of(user));
	        password.setUser(user);
	        when(passRepoMock.save(password)).thenReturn(password);
	        PasswordStored savedPassword = passService.addPassword(password);
	        assertEquals(password, savedPassword);
	    }
	}

	@Test
	void testUpdateById() {
		PasswordStored newPassword = new PasswordStored(1, "AmazonPrime", "john_doe_prime", "new_password", user);

		when(passRepoMock.existsById(1)).thenReturn(true);
		when(passRepoMock.findById(1))
				.thenReturn(Optional.of(new PasswordStored(1, "AmazonPrime", "john_doe_prime", "old_password", user)));
		when(userRepoMock.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		when(passRepoMock.save(any(PasswordStored.class))).thenReturn(newPassword);
		PasswordStored updatedPassword = passService.updateById(1, newPassword, user.getEmail());
		assertEquals(newPassword, updatedPassword);
	}

	@Test
	void testUpdateByIdNotFound() {
		PasswordStored newPassword = new PasswordStored(1, "AmazonPrime", "john_doe_prime", "new_password", null);
		when(passRepoMock.existsById(1)).thenReturn(false);
		assertThrows(CustomizedNotFoundException.class, () -> {
			passService.updateById(1, newPassword, null);
		});
	}

	@Test
	void testDeleteById() {
		when(passRepoMock.existsById(1)).thenReturn(true);
		assertDoesNotThrow(() -> passService.deleteById(1));
	}

	@Test
	void testDeleteByIdNotFound() {
		when(passRepoMock.existsById(1)).thenReturn(false);
		assertThrows(CustomizedNotFoundException.class, () -> {
			passService.deleteById(1);
		});
	}

}
