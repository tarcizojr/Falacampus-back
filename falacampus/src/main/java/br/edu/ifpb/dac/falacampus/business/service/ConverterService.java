package br.edu.ifpb.dac.falacampus.business.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.edu.ifpb.dac.falacampus.business.service.impl.SuapServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.SystemRoleServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;

@Service
public class ConverterService {

	@Autowired
	private SystemRoleServiceImpl roleService;

	@Autowired
	private DepartamentService departamentService;
	
	@Autowired
	private UserService userService;

	public String mapToJson(Map<String, String> map) {
		Gson gson = new Gson();
		String json = gson.toJson(map);
		return json;
	}

	public String jsonToToken(String json) {
		JsonElement jsonElement = JsonParser.parseString(json);

		String token = jsonElement.getAsJsonObject().get("token").getAsString();
		return token;
	}
	
	public String jsonToTokenDepartament(String json) {
		JsonElement jsonElement = JsonParser.parseString(json);
		
		String token = jsonElement.getAsJsonObject().get("nome").getAsString();
		return token;
	}

	public User jsonToUser(String jsonUser) {

		JsonElement jsonElement = JsonParser.parseString(jsonUser);
		JsonObject results = jsonElement.getAsJsonObject()
				.get("results")
				.getAsJsonArray()
				.get(0)
				.getAsJsonObject();
		
		
		String name = results.get("nome").getAsString();
		String username = results.get("matricula").getAsString();
		JsonElement office = results.get("cargo_emprego");
		
		
		User user = userService.findByUserName(username);
		
		if(user==null) {
			user = new User();
		}

		List<SystemRole> roles = new ArrayList<>();
		Departament departament = null;

		if (office != null) {
			roles.add(roleService.findByName(SystemRoleService.AVAILABLE_ROLES.EMPLOYEES.name()));

			JsonElement lotacao = results.get("lotacao_siape");
			JsonObject lotacaoJson = lotacao.getAsJsonObject();
			JsonElement sigla = lotacaoJson.get("sigla");
			JsonElement nameDepartament = lotacaoJson.get("nome");

			String departamentName = nameDepartament.getAsString();
			
			Departament findByNameDepartament = departamentService.findByName(departamentName);

			if (findByNameDepartament == null) {
				departament = departamentService.save(new Departament(departamentName));
			} else {
				departament = findByNameDepartament;
			}

		} else {
			roles.add(roleService.findDefault());

			JsonObject curso = results.get("curso").getAsJsonObject();
			JsonElement cursoNameJson = curso.get("nome");
			String cursoName = cursoNameJson.getAsString();
			
			
			Departament findByNameDepartament = departamentService.findByName(cursoName);

			if (findByNameDepartament == null) {
				departament = departamentService.save(new Departament(cursoName));
			} else {
				departament = findByNameDepartament;
			}
	
		}

		user.setName(name);
		user.setUsername(username);
		user.setRoles(roles);
		user.setDepartament(departament);
		user.setPassword("password");
		user.setEmail("email@ifpb.edu.br");
		
		if(user.getId() != null) {
			user = userService.update(user);
		}else {
			user = userService.save(user);
		}

		return user;
	}
	
//------------------	
	public JsonObject jsonToDepartament(String jsonDepartament) {
		JsonElement jsonE = JsonParser.parseString(jsonDepartament);
		
		JsonObject results = jsonE.getAsJsonObject().getAsJsonObject();
		
		return results;
	}

}
