package com.fdmgroup.passwordmanager.config;

import org.slf4j.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	private final TokenService tokenService;

	public AuthController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@GetMapping("/login")
	public String showLogin() {
		return "Page for login";
	}

	@PostMapping("/login")
	public String token(Authentication auth) {
		LOG.debug("Token requested for user: '{}'", auth.getName());
		String token = tokenService.generateToken(auth);
		LOG.debug("Token granted: {}", token);
		return token;
	}
	
	@GetMapping("/users/mp")
	public String showMpPrompt() {
		return "Page for mp prompt for vault access";
	}
	
	@PostMapping("/users/mp")
	public String mpToken(Authentication auth) {
		LOG.debug("Token requested for user for vault access: '{}'", auth.getName());
		String token = tokenService.generateToken(auth);
		LOG.debug("Token granted: {}", token);
		return token;
	}

	@GetMapping("/authuserinfo")
	public String example(Authentication auth) {
		System.out.println("name:  " + auth.getName());
		System.out.println("principle: " + auth.getPrincipal());
		System.out.println("creds: " + auth.getCredentials());
		System.out.println(auth.getAuthorities());
		return "Hello, " + auth.getName();
	}
}
