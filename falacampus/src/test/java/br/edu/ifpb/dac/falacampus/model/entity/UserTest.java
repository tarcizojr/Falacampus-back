package br.edu.ifpb.dac.falacampus.model.entity;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;
import org.junit.runner.OrderWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;


@Testable
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("User")
class UserTest {
	
	@Autowired
	private static Validator validator;
	
	@Autowired
	private static User user;
	
	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	    
	    user = new User();
		user.setName("Aaaaa");
		user.setEmail("email@email.com");
		user.setUsername("1");
		user.setDepartament(new Departament());
		user.setPassword("12345678");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"T", "Th","Thallyta Maria Medeiros xxxxxxxxxxxxxxxxxxxxxxxxx51", ""})
//	@ValueSource(strings = { "Th"})
	void nameTest(String name) {
		user.setName(name);
		
		Set<ConstraintViolation<User>> validacoes = validator.validateProperty(user, "name");		
		
		assertEquals(0, validacoes.size());
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"thallyta@email.com", "@email", "thallytaemail.com", ""})
//	@ValueSource(strings = {"thallyta@email.com"})
	void emailTest(String email) {
	
		user.setEmail(email);
		
		Set<ConstraintViolation<User>> validacoes = validator.validateProperty(user, "email");
		
		assertTrue(validacoes.isEmpty());	
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "1234567", "efrrrgtrstuvwxyzABC21", "d43b*C3**",""})
//	@ValueSource(strings = { "d43b*C3**"})
	void passwordValidTest(String password) {
		user.setPassword(password);

		Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");

		assertEquals(0, violations.size());
	}

}
