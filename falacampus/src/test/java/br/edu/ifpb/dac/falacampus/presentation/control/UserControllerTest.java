package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpb.dac.falacampus.business.service.DepartamentService;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

	@Autowired
	private AuthenticationController authenticationController;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private DepartamentService departamentService;
	
	@Autowired
	private static User user;
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();	
	}

	@Test
	void testNull() {
	      assertNotNull(userController);
	}

	
	@Test
	void getTest() throws Exception {
		user = userController.findById(1L);
		
		assertEquals("Tarcizo Leite Monteiro Filho", user.getName());
		
	}
	
	@Test
	void findByFilterHttpTest() throws Exception {
		user = userController.findById(1L);
		ResponseEntity<String> response = userController.findByFilter(1L, user.getName(), user.getEmail(), user.getUsername());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals("Tarcizo Leite Monteiro Filho", user.getName());
	}
	
	@Test
	void saveHttpTest() throws Exception {
		user = new User(1L, "Thallyta Silva", "tarc@gmail.com", "thallyta1", "123", departamentService.findById(1L));
		
		UserDto userDto = new UserDto(user);
		
		ResponseEntity<String> response = userController.save(userDto);
		System.out.println(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());	
		
//		UserDto userDto = new UserDto(user);
//		
//		assertDoesNotThrow(() -> userController.save(userDto));
	}
	@Test
	void saveTest() {
		user.setDepartament(new Departament("Dep 1"));
		UserDto userDto = new UserDto(user);

		assertDoesNotThrow(() -> userController.save(userDto));
	}

	

	@Test
	void deleteHttpTest() {
		user.setDepartament(new Departament("Dep 1"));
		UserDto userDto = new UserDto(user);

		ResponseEntity<String> response = userController.delete(user.getId());
		System.out.println(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void updateTest() throws Exception {
		user = userController.findById(1L);
		
		assertEquals("Tarcizo Leite Monteiro Filho", user.getName());
		
	}
	
	
	

}