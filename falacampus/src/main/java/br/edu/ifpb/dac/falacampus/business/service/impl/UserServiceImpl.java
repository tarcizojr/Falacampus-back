package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.falacampus.business.service.PasswordEnconderService;
import br.edu.ifpb.dac.falacampus.business.service.SystemRoleService;
import br.edu.ifpb.dac.falacampus.business.service.SystemRoleService.AVAILABLE_ROLES;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.UserRepository;

//Classe service que executa a lógica de autenticação 
//(contém a lógica para validar as credenciais de um usuário que está se autenticando)
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SystemRoleService roleService;
	@Autowired
	private PasswordEnconderService passwordEnconderService;

	@Override
	public User save(User user) {
		
		if (user.getId() != null) {
			throw new IllegalStateException("User is already in the database");
		}
		
		passwordEnconderService.encryptPassword(user);
		
		List<SystemRole> roles = new ArrayList<>();
		
		if(findAll().isEmpty()) {
			roles.add(roleService.findByName(AVAILABLE_ROLES.ADMIN.name()));
		}else {
			roles.add(roleService.findDefault());
		}

		user.setRoles(roles);
		
		return userRepository.save(user);
		
		}
	
	@Override
	public User update(User user) {
		User userUp = findById(user.getId());
		if (userUp == null) {
			throw new IllegalStateException("User id is null");
		}
		
		userUp.setEmail(user.getEmail());
		userUp.setUsername(user.getUsername());
		userUp.setName(user.getName());
		userUp.setDepartament(user.getDepartament());
		
		List<SystemRole> roles = new ArrayList<>();
		
		if(user.getRoles().get(0).getName().equals("ADMIN")) {
			roles.add(roleService.findAdmin());
		}else {
			roles.add(roleService.findDefault());		}
		
		
		
		
		user.setRoles(roles);
		
		
		return userRepository.save(user);
	}
	
	@Override
	public void delete(Long id) {
		User user = findById(id);

		if (user == null) {
			throw new IllegalStateException(String.format("Could not find a entity with id=%1", id));
		}

		userRepository.deleteById(id);
	}

	@Override
	public User findById(Long id) {
		if(id == null) {
			throw new IllegalStateException("Id cannot be null");
		}
		return userRepository.findById(id).get();
	}
	
	@Override
	public Iterable<User> find(User filter) {
		Example example = Example.of(filter,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return userRepository.findAll(example);

	}
	
	@Override
	public User findByUserName(String username) {
		if(username == null) {
			throw new IllegalStateException("Username cannot be null");
		}
		Optional<User> optional = userRepository.findByUsername(username); 
		
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Could not find any user with username %s", username));
		}
		return user;
	}

	@Override
	public ArrayList<User> findAll() {
		return (ArrayList<User>) userRepository.findAll();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}



	



	

}
