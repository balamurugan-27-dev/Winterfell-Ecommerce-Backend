package com.winterfell.ecommerce.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.winterfell.ecommerce.dto.LoginDto;
import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.repository.UserRepository;

@Service
public class AuthenticationService {
	
//	PasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	UserRepository userRepo;
	BCryptPasswordEncoder encoder;
	public AuthenticationService(UserRepository repo,BCryptPasswordEncoder encoder) {
		this.userRepo=repo;
		this.encoder = encoder;
	}

	public void reigsterUser(Users user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		
	}

	public void login(String mail) {
		// TODO Auto-generated method stub
		
	}
	
}
