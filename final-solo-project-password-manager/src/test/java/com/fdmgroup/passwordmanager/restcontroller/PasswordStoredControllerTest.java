package com.fdmgroup.passwordmanager.restcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;
import com.fdmgroup.passwordmanager.service.*;

@ExtendWith(MockitoExtension.class)
class PasswordStoredControllerTest {

	@Mock
	private PasswordStoredService passServiceMock;
	@Mock
	private UserService userServiceMock;	

	@InjectMocks
	private PasswordStoredController controller;

	private PasswordStored samplePassword;
	private User user;

	@BeforeEach
	void setUp() {
		samplePassword = new PasswordStored(1, "Amazon", "john_doe", "password", null);
		this.user = new User();
		user.setEmail("a@gmail.com");
	}

	@Test
	void testFindById() {
		when(passServiceMock.findById(1)).thenReturn(Optional.of(samplePassword));
		PasswordStored result = controller.findById(1);
		assertEquals(samplePassword, result);
	}

	@Test
	void testFindById_NotFound() {
		when(passServiceMock.findById(1)).thenReturn(Optional.empty());
		assertThrows(CustomizedNotFoundException.class, () -> controller.findById(1));
	}

	@Test
	void testFindByUsername() {
		when(passServiceMock.findByUsername("john_doe")).thenReturn(Optional.of(samplePassword));
		PasswordStored result = controller.findByUsername("john_doe");
		assertEquals(samplePassword, result);
	}

	@Test
	void testFindByUsername_NotFound() {
		when(passServiceMock.findByUsername("john_doe")).thenReturn(Optional.empty());
		assertThrows(CustomizedNotFoundException.class, () -> controller.findByUsername("john_doe"));
	}

	@Test
	void testFindByUserIdAndServiceName() {
		when(passServiceMock.findByUserIdAndServiceName(1, "Amazon")).thenReturn(Optional.of(samplePassword));
		PasswordStored result = controller.findByUserIdAndServiceName(1, "Amazon");
		assertEquals(samplePassword, result);
	}

	@Test
	void testFindByUserIdAndServiceName_NotFound() {
		when(passServiceMock.findByUserIdAndServiceName(1, "Amazon")).thenReturn(Optional.empty());
		assertThrows(CustomizedNotFoundException.class, () -> controller.findByUserIdAndServiceName(1, "Amazon"));
	}

	@Test
	void testAddPassword() {
		when(passServiceMock.addPassword(samplePassword)).thenReturn(samplePassword);
		PasswordStored result = controller.addPassword(samplePassword);
		assertEquals(samplePassword, result);
	}

	@Test
	void testUpdateById() {
		when(passServiceMock.updateById(eq(1), any(), any())).thenReturn(samplePassword);
		PasswordStored result = controller.updateById(1, samplePassword, user.getEmail());
		assertEquals(samplePassword, result);
	}

	@Test
	void testUpdateById_NotFound() {
		when(passServiceMock.updateById(eq(1), any(), any()))
				.thenThrow(new CustomizedNotFoundException("Password does not exist"));
		assertThrows(CustomizedNotFoundException.class, () -> controller.updateById(1, samplePassword, user.getEmail()));
	}

	@Test
	void testDeleteById() {
		doNothing().when(passServiceMock).deleteById(1);
		String result = controller.deleteById(1);
		assertEquals("Deleted password id: " + 1, result);
	}

	@Test
	void testDeleteById_NotFound() {
		doThrow(new CustomizedNotFoundException("Password does not exist")).when(passServiceMock).deleteById(1);
		assertThrows(CustomizedNotFoundException.class, () -> controller.deleteById(1));
	}

}
