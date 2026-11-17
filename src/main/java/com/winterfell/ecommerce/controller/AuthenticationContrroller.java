package com.winterfell.ecommerce.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winterfell.ecommerce.dto.LoginDto;
import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationContrroller {

	AuthenticationService authService;
	AuthenticationManager authManger;
	
	public AuthenticationContrroller(AuthenticationService authService , AuthenticationManager authManager) {
		this.authService=authService;
		this.authManger=authManager;
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody Users user) {
		authService.reigsterUser(user);
	}
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDto loginDto) {
		
		Authentication authentication=authManger.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));
		
		Users user=(Users) authentication.getPrincipal();
		
		authService.login(user.getMail());
	}
}
