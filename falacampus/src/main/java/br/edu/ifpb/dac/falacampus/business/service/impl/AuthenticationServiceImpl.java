package br.edu.ifpb.dac.falacampus.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import br.edu.ifpb.dac.falacampus.business.service.AuthenticationService;
import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;

import br.edu.ifpb.dac.falacampus.model.entity.User;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	private UserService userService;
	@Autowired
	private SuapService suapService;
	@Autowired
	private ConverterService converterService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${app.logintype}")
	private String logintype;
	
	private String suapToken;
	
	
	public String login(String username, String password) {
		
		return suapLogin(username, password);		
//			switch (logintype) {
//			case "suap": 
//				return suapLogin(username, password);
//			case "local":
//				return localLogin(username, password);
//			default:
//				return suapLogin(username, password);
//			}
		}
			
		private String localLogin(String username, String password) {
			
			Authentication authentication =  
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(username, password));
			
			User user = userService.findByUserName(username);

			return tokenService.generate(user);
		}
	
	private String suapLogin(String username, String password) {
		
		String jsonToken = suapService.login(username, password);
		this.suapToken = converterService.jsonToToken(jsonToken);
		if(this.suapToken == null) {
			throw new IllegalArgumentException("Incorrect E-mail or Password");
		}
		
		User user = userService.findByUserName(username);
	
		
		if(user == null) {
			String json = suapService.findUser(suapToken, username);
			user = converterService.jsonToUser(json);
		}

		return tokenService.generate(user);
	}

	@Override
	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return (User) authentication.getPrincipal();
	}

}
