package com.winterfell.ecommerce.service;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.winterfell.ecommerce.entity.Users;

@SuppressWarnings("serial")
public class CustomerDetails implements UserDetails {
	
	private Users user;
	
	
	public CustomerDetails(Users user) {
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName().toUpperCase()));
	}

	@Override
	public @Nullable String getPassword() {
	
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getMail();
	}

}
