package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;

class AuthenticationControllerTest {

	@Autowired
	private static User user;
	
	private MockMvc mockMvc;
	
	@Value("${password}")
	private String password;
	
	@Autowired
	private AuthenticationController authenticationController;

	@BeforeEach
	protected void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
		
	}

	@Test
	void testLogin() throws Exception {	
		LoginDto login = new LoginDto();
		login.setUsername("202025020004");
		login.setPassword(password);
		
		ResponseEntity<String> response = authenticationController.login(login);
		System.out.println(response);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
