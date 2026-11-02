package com.winterfell.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winterfell.ecommerce.entity.Users;
import com.winterfell.ecommerce.service.AuthenticationService;

@RestController
@RequestMapping("/api")
public class AuthenticationContrroller {

	AuthenticationService authService;
	
	public AuthenticationContrroller(AuthenticationService authService) {
		this.authService=authService;
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody Users user) {
		authService.reigsterUser(user);
	}
}
