package br.edu.ifpb.dac.falacampus.presentation.dto;

import java.util.Objects;

public class TokenDto {
	
	private String token;
	private UserDto user;
	
	public TokenDto() {
		// TODO Auto-generated constructor stub
	}
	public TokenDto(String token, UserDto user) {
		this.token =token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Username: "+user.getUsername();
	}
}
