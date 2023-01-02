package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.DepartamentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;

@Service
public class DepartamentConverterServiceImpl implements DepartamentConverterService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//------------
	@Autowired
	private DepartamentService departamentService;
//=---------------
	@Autowired
	private SuapService suapService;

	@Autowired
	private ConverterService converterService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;

	@Value("${app.logintype}")
	private String logintype;
	
	private String suapToken;

	private Departament departament;
	
	//------------
	
	@Override
	public List<DepartamentDto> departamentToDTO(List<Departament> entities) {
		List<DepartamentDto> dtos = new ArrayList<>();
		
		for (Departament dto : entities) {
			DepartamentDto entity = departamentToDTO(dto);
			dtos.add(entity);
		}
		return dtos;
	}

	@Override
	public Departament dtoToDepartament(DepartamentDto dto) {
		
		Departament entity = modelMapper.map(dto, Departament.class);
		//Departament entity = new Departament();
		
		//entity.setId(dto.getId());
		//entity.setName(dto.getName());
		
		return entity;
	}

	@Override
	public DepartamentDto departamentToDTO(Departament entity) {
		
		DepartamentDto dto = modelMapper.map(entity, DepartamentDto.class);
		//DepartamentDto dto = new DepartamentDto();
		
		//dto.setId(entity.getId());
		//dto.setName(entity.getName());
		
		return dto;
	}
	
	//----------------------

	public void SaveAllDepartments(String url) {
		//Converter token
		try {
			
			this.suapToken = converterService.jsonToTokenDepartament(suapService.findAllDepartament(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(this.suapToken == null) {
			throw new IllegalArgumentException();
		}
		String suapDepartamentJson = this.suapService.findAllDepartament(url);
		
	
		try {
			JsonObject result = converterService.jsonToDepartament(suapDepartamentJson);			
			departament = new Departament();
			
			String name = result.get("nome").getAsString().toString();
			departament.setName(name);
			
			String initials = result.get("sigla").getAsString().toString();

			departament.setAcronymDepartment(initials);
			
			departamentService.save(departament);

			JsonArray childSectors = result.get("setores_filho").getAsJsonArray();
			
			for (JsonElement jsonElement : childSectors) {
				
				SaveAllDepartments(jsonElement.toString());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
