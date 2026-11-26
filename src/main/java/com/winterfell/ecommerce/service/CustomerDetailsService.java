package com.winterfell.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.repository.UserRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {

	UserRepository userRepo;
	
	public CustomerDetailsService(UserRepository userRepo) {
		this.userRepo=userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepo.findByMail(username).orElseThrow(()->new UsernameNotFoundException("name not found"));
		
		return new CustomerDetails(user);
	}

	
}
