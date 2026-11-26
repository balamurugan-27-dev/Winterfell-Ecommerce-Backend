package com.winterfell.ecommerce.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.winterfell.ecommerce.dto.RegisterDto;
import com.winterfell.ecommerce.entity.Role;
import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.jwt.JwtUtil;
import com.winterfell.ecommerce.repository.RoleRepository;
import com.winterfell.ecommerce.repository.UserRepository;

@Service
public class AuthenticationService {
	
//	PasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	UserRepository userRepo;
	BCryptPasswordEncoder encoder;
	RoleRepository roleRepo;
	JwtUtil jwtUtil;
	public AuthenticationService(UserRepository repo,BCryptPasswordEncoder encoder,RoleRepository roleRepo,JwtUtil jwtUtil) {
		this.userRepo=repo;
		this.encoder = encoder;
		this.roleRepo=roleRepo;
		this.jwtUtil=jwtUtil;
	}

	public void reigsterUser(RegisterDto dto) {
		Users user=new Users();
		Role role = roleRepo.findById(dto.getRole())
			    .orElseThrow(() -> new RuntimeException("Role not found"));
		user.setName(dto.getName());
		user.setPassword(encoder.encode(dto.getPassword()));
		user.setMail(dto.getMail());
		user.setRole(role);
		userRepo.save(user);
		
	}

	public String  login(String mail) {
		return jwtUtil.generateToken(mail);
	}
	
}
