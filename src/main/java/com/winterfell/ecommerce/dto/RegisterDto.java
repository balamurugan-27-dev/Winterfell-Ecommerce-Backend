package com.winterfell.ecommerce.dto;

public class RegisterDto {
	
	private String name;
	private String mail;
	private String password;
	private Long role = 1L;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getRole() {
		return role;
	}
	public void setRole(Long roleId) {
		this.role = roleId;
	}
	
	
	
	
}
