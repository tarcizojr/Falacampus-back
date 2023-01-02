package br.edu.ifpb.dac.falacampus.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.DepartamentRepository;

@Service
public class DepartamentService {

	@Autowired
	private DepartamentRepository departamentRepository;
	
	
	public Departament findByName(String name) {
		return departamentRepository.findByName(name);
	}
	
	public Departament save(Departament departament) {

		return departamentRepository.save(departament);
	}

	public void deleteById(Long id) {

		Departament departament = findById(id);

		if (departament == null) {
			throw new IllegalStateException(String.format("Could not find a entity with id=%1", id));
		}

		departamentRepository.deleteById(id);
	}

	public Departament update(Departament departament) {
		Departament departamentUp = findById(departament.getId());
		departamentUp.setName(departament.getName());
		departamentUp.setResponsibleUsers(departament.getResponsibleUsers());
		
		

		
		return departamentRepository.save(departament);
	
	}

	public Departament update(Long id) {
		Departament departamentSalvo = departamentRepository.getById(id);
		if (id == null) {
			throw new IllegalStateException("Id cannot be null");
		}
		return departamentRepository.save(departamentSalvo);
	}

	public Departament findById(Long id) {

		if (id == null) {
			throw new IllegalStateException("Id cannot be null");
		}

		return departamentRepository.findById(id).get();
	}

	public List<Departament> findAll() {
		return departamentRepository.findAll();
	}

	public List<Departament> find(Departament filter) {
		Example example = Example.of(filter,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return departamentRepository.findAll(example);
	}	
}
