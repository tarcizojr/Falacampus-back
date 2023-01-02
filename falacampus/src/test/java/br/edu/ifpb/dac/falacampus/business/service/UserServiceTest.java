package br.edu.ifpb.dac.falacampus.business.service;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;


import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;

import br.edu.ifpb.dac.falacampus.model.repository.UserRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private List<User> usersMock;
	
	private static User user;
	
	@Spy
	private List<User> usersSpy = new ArrayList<User>();
	
	@Captor
	private ArgumentCaptor<User> argCaptor;
	
	@BeforeEach
	public void before() {
		user = new User(1L, "Maria", "", "", "", new Departament(1L, "D1"));
		this.userRepository =  mock(UserRepository.class);
		this.usersMock =  mock(List.class);		
		this.userService = mock(UserService.class);
		this.userServiceImpl = mock(UserServiceImpl.class);
		
	}
	
	@Test
	void testSave() {		
		verifyNoInteractions(userService); //verifica se há outras além das que chamei
		userService.save(user);
		verify(userService, times(1)).save(user);
	}
	
	@Test
	void testFindAllService() {
		when(userRepository.findAll()).thenReturn(usersMock);		
		when(usersMock.size()).thenReturn(10);		
		assertEquals(10, usersMock.size());
		verify(userRepository.findAll()).size();
	}
	
	@Test
	void testFindById() {
		when(userService.findById(null)).thenThrow(new IllegalStateException("Id cannot be null"));
		assertDoesNotThrow(() -> userService.findById(8L));
	}
	
	@Test
	void testDeleteById() {	
		doThrow(new IllegalStateException()).when(userService).delete(null);
		assertDoesNotThrow(() -> userService.delete(9L));
	}
	
	@Test
	void testUpdate() {
		when(usersMock.get(0)).thenReturn(new User(1l, "teste", "email@teste","2l" , "password123", new Departament()));
		when(userService.update(any(User.class))).thenReturn(new User(1l, "teste", "emailAtualizado@teste","2l" , "password123", new Departament()));
		
		String upEmail ="emailAtualizado@teste";
		String upName = "Maria";
		
		User user = usersMock.get(0);
		user.setEmail(upEmail);		
		user.setName(upName);
		
		User userUp = userService.update(user);
		String email = userUp.getEmail();
		
		assertEquals(upEmail, userUp.getEmail());
		assertEquals(upName, userUp.getName());
		
	}
	
	@Test
	void userRepositoryGetByIdReturnNull() {		
		User u = userRepository.getById(user.getId());
		assertNull(u);
	}
	

	@Test
	void userListTest() {
		User user1 = new User(2L, "João", null, null, null, null);
		
		usersSpy.add(user);
		usersSpy.add(user1);
		
	    assertEquals(2, usersSpy.size());	    
	    when(usersSpy.size()).thenReturn(100);		
	    assertEquals(100, usersSpy.size());
	 
	}
	
	@Test
	void testMockList() {	
		
		User user1 = new User(2L, "João", null, null, null, null);
		
		usersMock.add(user);
		usersMock.add(user1);
		
		assertEquals(0, usersMock.size());
		
//	    verify(usersMock,times(2)).add(argCaptor.capture());
//	    
//	    assertEquals(user,argCaptor.getAllValues().get(0));
//	    assertEquals(user1,argCaptor.getAllValues().get(1));
	}
	

}
