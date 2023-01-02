package br.edu.ifpb.dac.falacampus.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;

public class DepartamentDto {
	
	private Long id;
	
	@NotNull @NotEmpty @Size(min = 2, max=100)
	private String name;

	//------------
	private List<String> responsibleUsers;
	
	private String acronymDepartment;
	//------------
	
	

	public DepartamentDto() {
		
	}

	public DepartamentDto(Departament departament) {
	
	}
//		this.id = departament.getId();
//		this.name = departament.getName();
//		this.responsibleUsers = departament.getResponsibleUsers();
//		this.acronymDepartment = departament.getAcronymDepartment();
//	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getResponsibleUsers() {
		return responsibleUsers;
	}

	public void setResponsibleUsers(List<String> responsibleUsers) {
		this.responsibleUsers = responsibleUsers;
	}

	public String getAcronymDepartment() {
		return acronymDepartment;
	}

	public void setAcronymDepartment(String acronymDepartment) {
		this.acronymDepartment = acronymDepartment;
	}

	public static List<DepartamentDto> toConvert(List<Departament> departaments){
		return departaments.stream().map(DepartamentDto:: new).collect(Collectors.toList());
	}
}
