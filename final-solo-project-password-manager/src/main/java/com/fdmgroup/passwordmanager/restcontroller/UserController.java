package com.fdmgroup.passwordmanager.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.User;
import com.fdmgroup.passwordmanager.service.UserService;

/**
 * UserController is responsible for handling incoming HTTP requests
 * related to User entities.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	/**
     * Constructor that injects the UserService.
     * 
     * @param userService The service to handle user-related operations.
     */
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	/**
     * Retrieves a User by their ID.
     * 
     * @param id The ID of the User.
     * @return The User with the given ID.
     * @throws CustomizedNotFoundException if the User ID does not exist.
     */
	@GetMapping("/{id}")
	public User findByUserId(@PathVariable Integer id) {
	    return userService.findById(id)
	    		.orElseThrow(() -> new CustomizedNotFoundException("User ID does not exist"));
	}
	
	/**
     * Retrieves all Users.
     * 
     * @return A list of all Users.
     */
	@GetMapping("/all")
	public List<User> getUsers() {
		return userService.findAll();
	}
	
	/**
     * Retrieves a User by their email.
     *
     * @param email The email of the User.
     * @return The User with the given email.
     * @throws CustomizedNotFoundException if the User does not exist.
     */
	@GetMapping("/email/{email}")
	public User findByEmail(@PathVariable String email) {
		return userService.findByEmail(email)
				.orElseThrow(() -> new CustomizedNotFoundException("User does not exist"));
	}
	
	/**
     * Retrieves a User by their hashed password.
     *
     * @param hashedPassword The hashed password of the User.
     * @return The User with the given hashed password.
     * @throws CustomizedNotFoundException if the User does not exist.
     */
	@GetMapping("/pass/{hashedPassword}")
	public User findByHashedPassword(@PathVariable String hashedPassword) {
		return userService.findByHashedPassword(hashedPassword)
				.orElseThrow(() -> new CustomizedNotFoundException("User does not exist"));
	}
	
	/**
     * Adds a new User.
     *
     * @param user The User to be added.
     * @return The newly created User.
     */
	@PostMapping
	public User addUser(@RequestBody User user) {
		user.setId(0);	// indicate that the user is "new".
		User newUser = userService.registerUser(user);
		return newUser;
	}
	
	 /**
     * Updates a User by their ID.
     *
     * @param id   The ID of the User to be updated.
     * @param user The updated User information.
     * @return The updated User.
     */
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Integer id, @RequestBody User user) {
		User updatedUser = userService.updateById(id, user);
		return updatedUser;
	}
	
	/**
     * Deletes a User by their ID.
     *
     * @param id The ID of the User to be deleted.
     * @return A string message indicating successful deletion.
     * @throws CustomizedNotFoundException if the User ID does not exist.
     */
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userService.findById(id)
				.orElseThrow(() -> new CustomizedNotFoundException("User ID does not exist"));
		userService.deleteById(id);
		return "Deleted user id: " + id;
	}
	
	
}