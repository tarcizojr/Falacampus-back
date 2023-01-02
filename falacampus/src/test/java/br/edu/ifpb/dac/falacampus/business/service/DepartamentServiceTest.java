package br.edu.ifpb.dac.falacampus.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.DepartamentRepository;

class DepartamentServiceTest {
	
	@Mock
	private DepartamentRepository departamentRepository;
	
	@Mock
	private DepartamentService departamentService = new DepartamentService();
	
	@Mock
	private List<Departament> departaments;
	
	@Spy
	private List<User> usersSpy;
	
	@Captor
	private ArgumentCaptor<User> argCaptor;
	
	@BeforeEach
	public void before() {
		this.departamentRepository =  mock(DepartamentRepository.class);
		this.departaments =  mock(List.class);	
		this.departamentService = mock(DepartamentService.class);
	}
 
	@Test
	void testFindAllService() {
		when(departamentRepository.findAll()).thenReturn(departaments);		
		when(departaments.size()).thenReturn(10);		
		assertEquals(10, departaments.size());
		verify(departamentRepository.findAll()).size(); 													
	}
	
	@Test
	void testDeleteById() {
		doThrow(new IllegalStateException()).when(departamentService).deleteById(null);
		assertDoesNotThrow(() -> departamentService.deleteById(null));
	}
	
	@Test
	void testFindById () { 
		doThrow(new IllegalStateException()).when(departamentService).findById(null);
		assertDoesNotThrow(() -> departamentService.findById(9L));
	}

	@Test
	void testUpdate() {
		when(departaments.get(0)).thenReturn(new Departament (1l, "Coordenação de ADS")); 
		
		when(departamentRepository.save(any(Departament.class))).thenReturn(new Departament(1l, "Biblioteca"));
		
		String updatedDepartamentName ="Biblioteca";
		
		Departament departament = departaments.get(0);
		departament.setName(updatedDepartamentName);		
		
		Departament departamentUpdate = departamentRepository.save(departament);
		String name = departamentUpdate.getName();
		
		assertEquals(updatedDepartamentName, departamentUpdate.getName());
	}
	
	@Test
	void testSaveDepartament() {
		
		Departament d = new Departament(1L, "Dep 1");
		d.setUsers(usersSpy);
		
		departamentService.save(d);
		
		verify(departamentService, times(1)).save(d);
		
		assertEquals(usersSpy, d.getUsers());
//		verifyNoInteractions(departamentService); //verifica se há outras chamadas além das que chamei
	}
	

}
