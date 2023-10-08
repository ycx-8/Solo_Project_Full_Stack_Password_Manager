package com.fdmgroup.passwordmanager.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;
import com.fdmgroup.passwordmanager.service.*;

/**
 * PasswordStoredController handles HTTP requests related to PasswordStored entities.
 */
@RestController	@RequestMapping("/vault")
public class PasswordStoredController {
	
	private PasswordStoredService passService;
	private UserService userService;

	/**
	 * Constructor that injects the PasswordStoredService and UserService.
	 * 
	 * @param passService The service to handle password-related operations.
	 * @param userService The service to handle user-related operations.
	 */
	@Autowired
	public PasswordStoredController(PasswordStoredService passService, UserService userService) {
		this.passService = passService;
		this.userService = userService;
	}

	/**
	 * Retrieves a PasswordStored by its ID.
	 * 
	 * @param id The ID of the PasswordStored.
	 * @return The PasswordStored with the given ID.
	 * @throws CustomizedNotFoundException if the password does not exist.
	 */
	@GetMapping("{id}")
	public PasswordStored findById(@PathVariable Integer id) {
		return passService.findById(id)
				.orElseThrow(() -> new CustomizedNotFoundException("Password does not exist"));
	}
	
	/**
	 * Retrieves a PasswordStored by username.
	 * 
	 * @param username The username associated with the PasswordStored.
	 * @return The PasswordStored associated with the given username.
	 * @throws CustomizedNotFoundException if the username does not exist.
	 */
	@GetMapping("/username/{username}")
	public PasswordStored findByUsername(@PathVariable String username) {
		return passService.findByUsername(username)
				.orElseThrow(() -> new CustomizedNotFoundException("Username does not exist"));
	}
	
	/**
	 * Retrieves a PasswordStored by the user's ID and service name.
	 * 
	 * @param userId The ID of the user.
	 * @param serviceName The name of the service.
	 * @return The PasswordStored associated with the given user ID and service name.
	 * @throws CustomizedNotFoundException if the service name does not exist for this user.
	 */
	@GetMapping("/service/{userId}/{serviceName}")
	public PasswordStored findByUserIdAndServiceName(@PathVariable Integer userId, @PathVariable String serviceName) {
		return passService.findByUserIdAndServiceName(userId, serviceName)
				.orElseThrow(() -> new CustomizedNotFoundException("Service name does not exist for this user"));
	}
	
	/**
	 * Adds a new PasswordStored.
	 * 
	 * @param password The PasswordStored to be added.
	 * @return The newly added PasswordStored.
	 */
	@PostMapping
	public PasswordStored addPassword(@RequestBody PasswordStored password) {
		return passService.addPassword(password);
	}
	
	/**
	 * Updates a PasswordStored by its ID and associated email.
	 * 
	 * @param id The ID of the PasswordStored.
	 * @param password The updated PasswordStored information.
	 * @param email The associated email.
	 * @return The updated PasswordStored.
	 */
	@PutMapping("{id}/{email}")
	public PasswordStored updateById(@PathVariable Integer id, @RequestBody PasswordStored password, @PathVariable String email) {
		return passService.updateById(id, password, email);
	}
	
	/**
	 * Deletes a PasswordStored by its ID.
	 * 
	 * @param id The ID of the PasswordStored to be deleted.
	 * @return A string message indicating the deletion.
	 */
	@DeleteMapping("{id}")
	public String deleteById(@PathVariable Integer id) {
		passService.deleteById(id);
		return "Deleted password id: " + id;
	}
	
	/**
	 * Retrieves all PasswordStored entities associated with a given user email.
	 * 
	 * @param username The user email associated with the PasswordStored entities.
	 * @return A list of all PasswordStored entities for the given user email.
	 */
	@GetMapping("/username/{username}/passwords")
    public List<PasswordStored> getAllPasswordsForUser(@PathVariable String username) {  
        Optional<User> user = userService.findByEmail(username); 
        Integer userId = user.get().getId();
        return passService.findByUserId(userId); 
    }

}