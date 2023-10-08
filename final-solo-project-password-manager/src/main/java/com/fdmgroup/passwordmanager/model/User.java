package com.fdmgroup.passwordmanager.model;

import java.util.*;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a User entity in the system.
 */
@Entity 
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class User{

	/**
	 * Unique identifier for the User.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * Email address of the User.
	 * It is unique and cannot be null.
	 */
	@Column(name = "email", unique = true, nullable = false)
	@NonNull
	private String email;
	
	/**
	 * Hashed password of the User.
	 * Cannot be null.
	 */
	@Column(name = "hashed_password", nullable = false)
	@NonNull
	private String hashedPassword;
	
	/**
	 * List of stored passwords related to this User.
	 * Cascades all JPA (Java Persistence API) operations to the child entities.
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PasswordStored> passwords = new ArrayList<PasswordStored>();
	
}
