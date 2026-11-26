package com.winterfell.ecommerce.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil  {
	private  final SecretKey secretKey;
	public JwtUtil(@Value("${jwt.secretkey}") String  secret_key) {
		
		 this.secretKey= Keys.hmacShaKeyFor(secret_key.getBytes());
	}
	
	
	public String generateToken(String mail) {
		
		return Jwts.builder()
		.subject(mail)
		.signWith(secretKey)
		.issuedAt(new Date())
		.expiration(new Date(System.currentTimeMillis()+1000*(60*60)))
		.compact();
		
	}
	
	public Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T extractClaims(String token,Function<Claims,T> function) {
		Claims claims=extractAllClaims(token);
		return function.apply(claims);
	}
	
	public boolean tokenValidation(String token) {
		Date expiry= extractClaims(token, (c)->c.getExpiration());
		return expiry.after(new Date());
	}
	
	
}
