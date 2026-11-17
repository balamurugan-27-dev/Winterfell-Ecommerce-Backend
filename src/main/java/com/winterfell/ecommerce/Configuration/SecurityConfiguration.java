package com.winterfell.ecommerce.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		return http.csrf(csrf->csrf.disable())
				.cors(cors->{})
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request->request.requestMatchers("/api/auth/**").permitAll())
				.authorizeHttpRequests(request->request.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
				.build();				
	}
	
	@Bean
	public BCryptPasswordEncoder enoder() {
		return new BCryptPasswordEncoder(12);
	}
	
}
