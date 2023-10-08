package com.fdmgroup.passwordmanager.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.passwordmanager.exceptions.CustomizedNotFoundException;
import com.fdmgroup.passwordmanager.model.*;

/**
 * Service class for handling business logic related to PasswordStored entities.
 */
@Service
public class PasswordStoredService {

	private PasswordStoredRepository passRepo;
	private UserRepository userRepo;

	/**
	 * Constructor to initialize PasswordStoredService with repositories.
	 *
	 * @param passRepo PasswordStored repository.
	 * @param userRepo User repository.
	 */
	@Autowired
	public PasswordStoredService(PasswordStoredRepository passRepo, UserRepository userRepo) {
		this.passRepo = passRepo;
		this.userRepo = userRepo;
	}

	/**
	 * Finds a PasswordStored by its ID.
	 *
	 * @param id The ID of the PasswordStored entity.
	 * @return An Optional<PasswordStored> object.
	 */
	public Optional<PasswordStored> findById(Integer id) {
		return passRepo.findById(id);
	}

	/**
	 * Finds a PasswordStored by its username.
	 *
	 * @param username The username related to the PasswordStored.
	 * @return An Optional<PasswordStored> object.
	 */
	public Optional<PasswordStored> findByUsername(String username) {
		return passRepo.findByUsername(username);
	}

	/**
	 * Finds a PasswordStored by the User ID and Service Name.
	 *
	 * @param userId      The ID of the user.
	 * @param serviceName The name of the service.
	 * @return An Optional<PasswordStored> object.
	 */
	public Optional<PasswordStored> findByUserIdAndServiceName(Integer userId, String serviceName) {
		return passRepo.findByUserIdAndServiceName(userId, serviceName);
	}

	/**
	 * Finds all PasswordStored entities.
	 *
	 * @return A list of PasswordStored objects.
	 */
	public List<PasswordStored> findAll() {
		return passRepo.findAll();
	}

	/**
	 * Adds a new PasswordStored entity.
	 *
	 * @param password The PasswordStored entity to add.
	 * @return The newly added PasswordStored entity.
	 */
	public PasswordStored addPassword(PasswordStored password) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		password.setUser(user);
		return passRepo.save(password);
	}

	/**
	 * Updates a PasswordStored entity by its ID.
	 *
	 * @param id       The ID of the PasswordStored entity.
	 * @param password The updated PasswordStored data.
	 * @param email    The email of the user.
	 * @return The updated PasswordStored entity.
	 */
	public PasswordStored updateById(Integer id, PasswordStored password, String email) {
		if (!passRepo.existsById(id)) {
			throw new CustomizedNotFoundException("Password does not exist");
		}
		PasswordStored existingPassword = passRepo.findById(id).orElseThrow();  
		Optional<User> userId = userRepo.findByEmail(email);
		existingPassword.setServiceName(password.getServiceName());
	    existingPassword.setUsername(password.getUsername());
	    existingPassword.setPassword(password.getPassword());
		existingPassword.setUser(userId.get());
		return passRepo.save(existingPassword);
	}

	/**
	 * Deletes a PasswordStored entity by its ID.
	 *
	 * @param id The ID of the PasswordStored entity.
	 */
	public void deleteById(Integer id) {
		if (!passRepo.existsById(id)) {
			throw new CustomizedNotFoundException("Password does not exist");
		}
		passRepo.deleteById(id);
	}

	/**
	 * Finds PasswordStored entities by User ID.
	 *
	 * @param userId The ID of the user.
	 * @return A list of PasswordStored entities.
	 */
	public List<PasswordStored> findByUserId(Integer userId) {
		return passRepo.findByUserId(userId);
	}

}