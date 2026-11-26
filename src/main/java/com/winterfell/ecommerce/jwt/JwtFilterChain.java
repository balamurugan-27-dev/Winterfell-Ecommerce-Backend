package com.winterfell.ecommerce.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.winterfell.ecommerce.repository.UserRepository;
import com.winterfell.ecommerce.service.CustomerDetailsService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilterChain extends OncePerRequestFilter {
	

	UserRepository userRepo;
	JwtUtil jwtUtil;
	CustomerDetailsService customerDetailsService;
	
	public JwtFilterChain(	UserRepository userRepo,	JwtUtil jwtUtil, CustomerDetailsService customerDetailsService) {
		this.jwtUtil=jwtUtil;
		this.userRepo=userRepo;
		this.customerDetailsService=customerDetailsService;
	}
	

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println("JWT FILTER EXECUTED");

    String header = request.getHeader("Authorization");

    // Only validate when a Bearer token exists
    if (header != null && header.startsWith("Bearer ")) {

        try {
            String token = header.substring(7);

            String userMail = jwtUtil.extractClaims(
                    token, Claims::getSubject);

            if (userMail != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails user =
                        customerDetailsService.loadUserByUsername(userMail);

                if (jwtUtil.tokenValidation(token)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities());

                    auth.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(auth);

                } else {
                    response.sendError(401, "Invalid token");
                    return;
                }
            }

        } catch (Exception e) {

            System.out.println("JWT ERROR: " + e.getMessage());

            SecurityContextHolder.clearContext();

            response.sendError(401, "Invalid token");
            return;
        }
    }

    // Execute the remaining filter chain only once
    System.out.println("JWT FILTER PASSED");

    filterChain.doFilter(request, response);
}

}
