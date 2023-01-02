package br.edu.ifpb.dac.falacampus.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Departament implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="departament_id")
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(min = 2, max=100)
	@Column(name = "departament_name")
	private String name;
	 
	@OneToMany(mappedBy = "departament")
	private List<User> users = new ArrayList<>();

	
	//------------
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	private List<User> responsibleUsers;

	@ManyToMany
	@JoinTable(name = "departaments_responsibleUsers", joinColumns = @JoinColumn(name = "departament_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> responsibleUsers;
	
	private String acronymDepartment;
	//------------


	public Departament() {

	}

	public Departament(Long id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	//	this.responsibleUsers = responsibleUsers;
	}
	
	public Departament(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Departament(String name) {
		
		this.name = name;
	}

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	public List<User> getResponsibleUsers() {
		return responsibleUsers;
	}

	public void setResponsibleUsers(List<User> responsibleUsers) {
		this.responsibleUsers = responsibleUsers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getAcronymDepartment() {
		return acronymDepartment;
	}

	public void setAcronymDepartment(String acronymDepartment) {
		this.acronymDepartment = acronymDepartment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acronymDepartment, id, name, responsibleUsers, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departament other = (Departament) obj;
		return Objects.equals(acronymDepartment, other.acronymDepartment) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(responsibleUsers, other.responsibleUsers)
				&& Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "Departament [id=" + id + ", name=" + name + ", users=" + users + ", responsibleUsers="
				+ responsibleUsers + ", acronymDepartment=" + acronymDepartment + "]";
	}

	

	

	


}
