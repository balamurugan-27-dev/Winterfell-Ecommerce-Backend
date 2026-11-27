package com.winterfell.ecommerce.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winterfell.ecommerce.dto.LoginDto;
import com.winterfell.ecommerce.dto.RegisterDto;
import com.winterfell.ecommerce.service.AuthenticationService;
import com.winterfell.ecommerce.service.CustomerDetails;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("https://winterfell-ecommerce-zaya-git-main-balamurugan2.vercel.app")	
public class AuthenticationContrroller {

	AuthenticationService authService;
	AuthenticationManager authManger;
	
	public AuthenticationContrroller(AuthenticationService authService , AuthenticationManager authManager) {
		this.authService=authService;
		this.authManger=authManager;
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody RegisterDto user) {
		authService.reigsterUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto) {
		
		
		try {

		Authentication authentication=authManger.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));
		CustomerDetails user= (CustomerDetails) authentication.getPrincipal();
		return authService.login(user.getUsername());
		}
		catch(Exception e) {
			   e.printStackTrace();
			    throw e;
		}
	}
	
	@PostMapping("/test")
	public String test(){
		System.out.println("gshagois");
		return "hiii";
	}
}
