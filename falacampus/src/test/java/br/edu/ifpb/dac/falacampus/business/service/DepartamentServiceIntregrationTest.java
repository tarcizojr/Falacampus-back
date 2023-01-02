package br.edu.ifpb.dac.falacampus.business.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
@AutoConfigureMockMvc
@SpringBootTest
class DepartamentServiceIntregrationTest {
	
	@Autowired
	private DepartamentService departamentService;


	@Test
	void returnUsernameTest() {
		Departament dep = departamentService.findByName("502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)");
		assertEquals(1L, dep.getId());
		assertEquals("502 - Tecnologia em Análise e Desenvolvimento de Sistemas - Monteiro (CAMPUS MONTEIRO)", dep.getName());

	}

}
