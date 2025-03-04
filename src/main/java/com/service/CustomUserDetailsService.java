package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.repo.UserRepo;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByEmail(username);
		
		if(user==null) {
			throw new ResourceNotFoundException("User", "email:"+username, 0);
		}
		else {
		return user;
	}

}}
