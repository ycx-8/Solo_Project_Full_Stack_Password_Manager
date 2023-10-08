package com.fdmgroup.passwordmanager.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository interface for User-related database operations.
 * Extends Spring Data JPA's JpaRepository to leverage CRUD operations and JPA functionality.
 */
@Repository 
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
     * Finds a User entity by its email.
     * 
     * @param email The email of the User.
     * @return An Optional<User> which contains the User entity if found.
     */
	Optional<User> findByEmail(String email);
	
	/**
     * Finds a User entity by its hashed password.
     * 
     * @param hashedPassword The hashed password of the User.
     * @return An Optional<User> which contains the User entity if found.
     */
	Optional<User> findByHashedPassword(String hashedPassword);
}
