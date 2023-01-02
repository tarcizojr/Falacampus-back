package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.SystemRoleService.AVAILABLE_ROLES;
import br.edu.ifpb.dac.falacampus.business.service.impl.SystemRoleServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.DepartamentRepository;
import br.edu.ifpb.dac.falacampus.model.repository.UserRepository;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DepartamentControllerIntegrationTest {

	private final String url = "http://localhost:8080/api/departament";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njk4NjM2NTEsInN1YiI6IjEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiMjAyMDE1MDIwMDMyIiwiZXhwaXJhdGlvblRpbWUiOiIwMDowMCJ9.MoCqDwscnTYHmUZ215YrnFjESFCKZmrr98I-RehxJGI";
	@Autowired
	private Gson gson = new Gson();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DepartamentController departamentController;
	
	@Autowired
	private DepartamentRepository departamentRepository;
	
	@Autowired
	private SystemRoleServiceImpl roleServiceImpl;
	
	private ConverterService converterService;
	
	private Departament departament;
	
	private DepartamentDto departamentDto;

	
	private User user;
	
	@BeforeEach
	void setUp() {		
		departamentDto = new DepartamentDto();
		departamentDto.setName("Cord de ADS");
		
			
	}
	
	@Test
	@DisplayName("Listando usuários cadastrados")
	@Order(1)
	void list() throws Exception{
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
//	@Test
//	@DisplayName("Buscando um usuário já cadastrado")
//	void buscar() throws Exception {
//		mockMvc.perform(get(url + "/" + 2L)
//				.header("Authorization", auth))
//				.andExpect(status().isOk());
//	}
	
	@Test
	@DisplayName("Salvar um departamento com os dados corretos")
	@Order(2)
	void saveCorrect() throws Exception{

		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.gson.toJson(departamentDto)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Atualizar um departamento")
	@Order(3)
	void updateCorrect() throws Exception{
		departamentDto.setName("CORD de Edificação");
		
		
		
		
		mockMvc.perform(put(url + "/" + 1L)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON)				
				.content(this.gson.toJson(departamentDto)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Salvar um departamento com nome  inválido")
	@Order(4)
	void saveUserNameInvalid() throws Exception{
		departamentDto.setName("A");

		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.gson.toJson(departamentDto)))
				.andExpect(status().is(400));

		//Código de erro CREATE
		//Dados inválidos retorna 400
	}
	
	@Test
	@DisplayName("Salvar um departamento com usuário nulo")
	@Order(5)
	void saveUserDepartamentNull() throws Exception{
		departamentDto.setResponsibleUsers(null);

		mockMvc.perform(post(url)
				.header("Authorization", auth)
			.contentType(MediaType.APPLICATION_JSON)
				.content(this.gson.toJson(departamentDto)))
				.andExpect(status().isCreated());
		//Código de erro 201

	}
	
	@Test
	@DisplayName("Deletar um departamento existente")
	@Order(6)
	void deleteUser() throws Exception {
		mockMvc.perform(delete(url + "/" + 3L)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
		//Código de erro 204
		
	
	}
	
	@Test
	@DisplayName("Deletar um departamento que não existe")
	@Order(7)
	void deleteUserInvalid() throws Exception {
		mockMvc.perform(delete(url + "/" + 40L)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
		//Código de erro 400
	}
	

}