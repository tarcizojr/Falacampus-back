package br.edu.ifpb.dac.falacampus.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.test.context.TestPropertySource;

import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
class UserRepositoryIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	

	@BeforeEach
	void setUp() throws Exception {
		}
	@Test
	void repositoryNotNull() {
	      assertNotNull(userRepository);
		
	}

	@Test
	void returnUsernameTest() {
		Optional<User> optional = userRepository.findByUsername("202015020032");
		
		assertTrue(optional.isPresent());
		
		User user = optional.get();
		assertEquals(1L, user.getId());
		assertEquals("Tarcizo Leite Monteiro Filho", user.getName());
		assertEquals("email@ifpb.edu.br", user.getEmail());
	}

}
