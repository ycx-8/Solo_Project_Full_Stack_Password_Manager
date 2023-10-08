package com.fdmgroup.passwordmanager.dataloader;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fdmgroup.passwordmanager.model.*;
import com.fdmgroup.passwordmanager.service.UserService;

/**
 * DataLoader is a component class responsible for pre-loading dummy data into the application.
 * 
 * This class implements the CommandLineRunner interface, which enables it to run specific 
 * logic once the application context has fully started. In this case, it adds dummy data for 
 * both User and PasswordStored entities.
 */
@Component
public class DataLoader implements CommandLineRunner {

	private final UserService userService;
	private final PasswordStoredRepository passStoredRepo;
	
	/**
     * Constructs the DataLoader object by injecting dependencies.
     * 
     * @param userService The service class for handling user-related logic.
     * @param passStoredRepo The repository for handling PasswordStored entities.
     */
	@Autowired
	public DataLoader(UserService userService, PasswordStoredRepository passStoredRepo) {
		this.userService = userService;
		this.passStoredRepo = passStoredRepo;
	}

	/**
     * Executes the run method to add dummy data after the application has fully started.
     * 
     * @param args Command line arguments. Not used in this implementation.
     * @throws Exception if an error occurs during data loading.
     */
	@Override
	public void run(String... args) throws Exception {
		SecureRandom secureRandom = new SecureRandom();

		for (int i = 1; i <= 5; i++) {
			// Dummy User data
			User user = new User();
			int randomNumber = secureRandom.nextInt(100000);
			if (i % 2 == 0) {
				user.setEmail("dummyPerson" + randomNumber + "@gmail.com");
			} else {
				user.setEmail("dummyPerson" + randomNumber + "@hotmail.com");
			}
			String plainLoginPassword = "pass";
			user.setHashedPassword(plainLoginPassword);
			userService.registerUser(user);

			// Dummy PasswordStored data
			PasswordStored instaPassStored = new PasswordStored();
			instaPassStored.setServiceName("Instagram");
			instaPassStored.setUsername("dummyP" + secureRandom.nextInt(100000));
			String plainInstaPassword = "pass2";
			instaPassStored.setPassword(plainInstaPassword);
			instaPassStored.setUser(user);
			passStoredRepo.save(instaPassStored);

			PasswordStored amazonPassStored = new PasswordStored();
			amazonPassStored.setServiceName("Amazon");
			amazonPassStored.setUsername(user.getEmail());
			String plainAmazonPassword = "pass3";
			amazonPassStored.setPassword(plainAmazonPassword);
			amazonPassStored.setUser(user);
			passStoredRepo.save(amazonPassStored);
		}
	}

}
