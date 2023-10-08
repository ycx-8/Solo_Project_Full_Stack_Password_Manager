package com.fdmgroup.passwordmanager.model;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The PasswordStoredRepository interface for CRUD operations on PasswordStored entities.
 * 
 * This interface extends Spring's JpaRepository and is annotated as a @Repository.
 * It provides methods to find PasswordStored entities based on various attributes.
 */
@Repository
public interface PasswordStoredRepository extends JpaRepository<PasswordStored, Integer>{

	/**
     * Finds a PasswordStored entity based on the provided username.
     *
     * @param username The username associated with the PasswordStored entity.
     * @return An Optional containing the found PasswordStored entity, or empty if not found.
     */
	Optional<PasswordStored> findByUsername(String username);
	
	/**
     * Finds a PasswordStored entity based on the provided user ID and service name.
     *
     * @param userId The user ID associated with the PasswordStored entity.
     * @param serviceName The service name associated with the PasswordStored entity.
     * @return An Optional containing the found PasswordStored entity, or empty if not found.
     */
	Optional<PasswordStored> findByUserIdAndServiceName(Integer userId, String serviceName);
	
	/**
     * Finds all PasswordStored entities associated with a specific user ID.
     *
     * @param userId The user ID associated with the PasswordStored entities.
     * @return A list of PasswordStored entities that match the given user ID.
     */
	List<PasswordStored> findByUserId(Integer userId);
}