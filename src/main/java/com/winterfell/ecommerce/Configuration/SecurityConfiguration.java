package com.winterfell.ecommerce.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.winterfell.ecommerce.jwt.JwtFilterChain;
import com.winterfell.ecommerce.service.CustomerDetailsService;
import static org.springframework.http.HttpMethod.OPTIONS;
@Configuration
public class SecurityConfiguration {
	
	CustomerDetailsService customerDetailsService;
	JwtFilterChain jwtFilter;
	
	public SecurityConfiguration( CustomerDetailsService customerDetailsService,JwtFilterChain jwtFilter) {
		this.customerDetailsService=customerDetailsService;
		this.jwtFilter=jwtFilter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		return http.csrf(csrf->csrf.disable())
				.cors(cors->{})
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request->request.requestMatchers("/api/auth/**").permitAll())
				.authorizeHttpRequests(request->request.requestMatchers("/api/admin/**").hasRole("ADMIN"))
				.authorizeHttpRequests(request->request.requestMatchers("/api/saveproduct").hasRole("ADMIN")
				.anyRequest().authenticated())
				  .exceptionHandling(exception -> exception

				            // Not authenticated: 401
				            .authenticationEntryPoint((request, response, authException) -> {
				            	  response.setStatus(HttpStatus.UNAUTHORIZED.value()); 
				            })

				            // Authenticated but insufficient permissions: 403
				            .accessDeniedHandler((request, response, accessException) -> {
				        
				            	    response.setStatus(HttpStatus.FORBIDDEN.value());
				            })
				        )
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();				
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public AuthenticationProvider authprovider( ) {
		 DaoAuthenticationProvider provider= new DaoAuthenticationProvider(customerDetailsService);
		 provider.setPasswordEncoder(encoder());
		 return provider;
	}
	
	@Bean
	public AuthenticationManager manger() {
		return new ProviderManager(authprovider());
	}
	
	@Bean
	public FilterRegistrationBean<JwtFilterChain> jwtFilterRegistration(
	        JwtFilterChain filter) {

	    FilterRegistrationBean<JwtFilterChain> registration =
	            new FilterRegistrationBean<>(filter);

	    registration.setEnabled(false);

	    return registration;
	}
}
