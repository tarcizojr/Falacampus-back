package br.edu.ifpb.dac.falacampus.business.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpb.dac.falacampus.business.service.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;


class UserConverterServiceImplTest {

	@Autowired
	private static User user;
	
	@Autowired
	private static UserDto userDto;
	
	@Autowired
	private static UserConverterServiceImpl converterServiceImpl= new UserConverterServiceImpl();

	
	@BeforeAll
	static void initAll() {
		//Para criar um user, é obrigatorio 
		//nome (min=2 e max = 50)
		//email (@____.___
		//password
		//departamentId (tem q passar o id de uma departamento ja existente)
		//registration (pode ser branco)
		user = new User();
		user.setName("Tarcizo");
		user.setEmail("tarcizo@gmail.com");
		user.setPassword("123");
		
	}
	
	@Test
	void converterUserParaUserDtoTest() {
		converterServiceImpl = new UserConverterServiceImpl();
		UserDto userDto = converterServiceImpl.userToDTO(user);
		
		assertAll("userToDto", () -> assertEquals(userDto.getId(), user.getId()),
				() -> assertEquals(userDto.getName(), user.getName()),
				() -> assertEquals(userDto.getEmail(), user.getEmail()),
				() -> assertEquals(userDto.getUsername(), user.getUsername()));
	
		assertEquals(UserDto.class, userDto.getClass());
	}
	
	@Test
	public void dtoToUserTest() {
		
		User user = converterServiceImpl.dtoToUser(userDto);

//		assertAll("dtoToUser", 
//				() -> assertEquals(user.getId(), userDto.getId()),
//				() -> assertEquals(user.getName(), userDto.getName()), 
//				() -> assertEquals(user.getEmail(), userDto.getEmail()),
//				() -> assertEquals(user.getRegistration(), userDto.getRegistration()));
		
		
		assertEquals(user.getId(), userDto.getId());
		assertEquals(user.getName(), userDto.getName());
		assertEquals(user.getEmail(), userDto.getEmail());
		assertEquals(user.getUsername(), userDto.getUsername());

		assertEquals(User.class, userDto.getClass());

	}
}	
	
