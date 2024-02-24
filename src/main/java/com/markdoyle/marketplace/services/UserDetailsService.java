package com.markdoyle.marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.markdoyle.marketplace.entities.User;
import com.markdoyle.marketplace.repositories.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);

		if (user == null) {
			System.out.println("no user found");
			throw new UsernameNotFoundException("User not found: " + username);
		}

		System.out.println(user.username);
		System.out.println(user.password);

		return user;
	}
}
