package com.winterfell.ecommerce.service;

import org.springframework.stereotype.Service;
import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.repository.UserRepository;

@Service
public class AuthenticationService {
	
//	PasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	UserRepository userRepo;
	public AuthenticationService(UserRepository repo) {
		this.userRepo=repo;
	}

	public void reigsterUser(Users user) {
		userRepo.save(user);
	}
	
}
