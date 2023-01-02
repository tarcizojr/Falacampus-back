package br.edu.ifpb.dac.falacampus.model.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
@AutoConfigureMockMvc
@SpringBootTest
class DepartamentRepositoryIntegrationTest {
	
	@Autowired
	private DepartamentRepository departamentRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
	      assertNotNull(departamentRepository);

	}
	@Test
	void returnUsernameTest() {
		Departament dep = departamentRepository.findByName("502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)");
		
		assertEquals(1L, dep.getId());
		assertEquals("502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)", dep.getName());

	}


}
