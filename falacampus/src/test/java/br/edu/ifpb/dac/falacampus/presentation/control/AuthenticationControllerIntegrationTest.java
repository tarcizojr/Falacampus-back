package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ifpb.dac.falacampus.business.service.AuthenticationService;
import br.edu.ifpb.dac.falacampus.business.service.impl.AuthenticationServiceImpl;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.TokenDto;
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticationControllerIntegrationTest {

	
	@Value("${password}")
	private String password;
	
	private final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njk4NjM2NTEsInN1YiI6IjEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiMjAyMDE1MDIwMDMyIiwiZXhwaXJhdGlvblRpbWUiOiIwMDowMCJ9.MoCqDwscnTYHmUZ215YrnFjESFCKZmrr98I-RehxJGI";

	@Autowired
	private AuthenticationController authenticationController;

	private static LoginDto loginDto;	

	 @BeforeAll
	 static void iniciar() {
		
	   }
	 
	@Test
	@DisplayName("Endpoint de login")
	void testLogin() {
		loginDto = new LoginDto();
		loginDto.setUsername("202015020032");
		loginDto.setPassword(password);
		ResponseEntity<String> response = authenticationController.login(loginDto);
//			System.out.println(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());		
		
	}
	@Test
	@DisplayName("Retorna ok se o token é válido")
	void testIsValidToken() {
		ResponseEntity<String> response = authenticationController.isTokenValid(token);
//		System.out.println(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());	
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());		


		
	}
}