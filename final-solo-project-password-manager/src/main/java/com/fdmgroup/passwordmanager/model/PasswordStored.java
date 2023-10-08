package com.fdmgroup.passwordmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

/**
 * The PasswordStored entity class to map passwords stored in the system.
 * 
 * This class is annotated as a JPA entity, and Lombok annotations are used to generate 
 * boilerplate code such as getters, setters, and constructors.
 */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PasswordStored {

	/**
     * The unique identifier for a stored password.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
     * The service name or website where the password is used.
     * This is a mandatory field.
     */
	@Column(name = "website_name", nullable = false)
	private String serviceName;
	
	/**
     * The username for the service or website.
     * This is a mandatory field.
     */
	@Column(name = "account_username" , nullable = false)
	private String username;
	
	/**
     * The actual password for the service or website.
     * This is a mandatory field.
     */
	@Column(name = "account_password", nullable = false)
	private String password;
	
	/**
     * The User entity associated with this password.
     * Utilizes ManyToOne relationship and is referenced back in the JSON output to prevent recursion.
     */
	@ManyToOne @JsonBackReference
	@JoinColumn(name = "user_ID", referencedColumnName = "id")
	private User user;
	
}