package com.fdmgroup.passwordmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.fdmgroup.passwordmanager.model.User;
import com.fdmgroup.passwordmanager.model.UserRepository;

import lombok.Getter;

@Service @Getter
public class AuthUserService implements UserDetailsService{
	
	private UserRepository userRepo;
	@Autowired
	public AuthUserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
			return new AuthUser(user);  
	}

}