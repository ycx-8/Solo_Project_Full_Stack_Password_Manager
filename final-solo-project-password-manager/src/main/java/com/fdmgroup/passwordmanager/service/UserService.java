package com.fdmgroup.passwordmanager.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;

/**
 * Service layer for managing User entities.
 */
@Service
public class UserService {

	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;

	/**
	 * Constructor to initialize the UserService.
	 * 
	 * @param userRepo UserRepository instance.
	 * @param passwordEncoder PasswordEncoder instance.
	 */
	@Autowired
	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Finds a User by its ID.
	 * 
	 * @param id User's ID.
	 * @return Optional containing User or empty.
	 */
	public Optional<User> findById(Integer id) {
		return userRepo.findById(id);
	}

	/**
	 * Finds a User by email.
	 * 
	 * @param email User's email.
	 * @return Optional containing User or empty.
	 */
	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	/**
	 * Finds a User by hashed password.
	 * 
	 * @param hashedPassword User's hashed password.
	 * @return Optional containing User or empty.
	 */
	public Optional<User> findByHashedPassword(String hashedPassword) {
		return userRepo.findByHashedPassword(hashedPassword);
	}

	/**
	 * Retrieves all Users.
	 * 
	 * @return List of Users.
	 */
	public List<User> findAll() {
		return userRepo.findAll();
	}

	/**
	 * Adds a new User.
	 * 
	 * @param user User entity.
	 * @return Saved User.
	 */
	public User addUser(User user) {
		return userRepo.save(user);
	}

	/**
	 * Updates a User by ID.
	 * 
	 * @param id User's ID.
	 * @param user Updated User entity.
	 * @return Updated User.
	 */
	public User updateById(Integer id, User user) {
		if (!userRepo.existsById(id)) {
			throw new CustomizedNotFoundException("User ID does not exist");
		}
		if (user.getEmail() != null && !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Invalid email format");
		}
		user.setId(id);
		user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
		return userRepo.save(user);
	}

	/**
	 * Deletes a User by ID.
	 * 
	 * @param id User's ID.
	 */
	public void deleteById(Integer id) {
		if (!userRepo.existsById(id)) {
			throw new CustomizedNotFoundException("User ID does not exist");
		}
		userRepo.deleteById(id);
	}

	/**
	 * Registers a new User.
	 * 
	 * @param user User entity.
	 * @return Registered User.
	 */
	public User registerUser(User user) {
		user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
		return this.userRepo.save(user);
	}

}
