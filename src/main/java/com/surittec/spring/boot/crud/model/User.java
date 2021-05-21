package com.surittec.spring.boot.crud.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	private long id;
	private String username;
	private String password;
	private String name;
	private String cpf;
	private String address;
	private String cep;
	private String neighborhood;
	private String city;
	private String state;
	private String complement;
	private Boolean editPermission;
	private List<Email> emails;
	private List<Phone> phones;
	
	public User() {
		
	}
	
	public User(String username, String name, String cpf, String address, String cep, String neighborhood, String city, String state, String complement, List<Email> emails, List<Phone> phones, Boolean editPermission) {
		this.username = username;
		this.name = name;
		this.cpf = cpf;
		this.address = address;
		this.cep = cep;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.complement = complement;
		this.emails = emails;
		this.phones = phones;
		this.editPermission = editPermission;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "username", nullable = false, unique = true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "cpf", nullable = false, unique = true)
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "cep", nullable = false)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "neighborhood", nullable = false)
	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "complement", nullable = true)
	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Column(name = "password", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "edit_permission", nullable = true)
	public Boolean getEditPermission() {
		return editPermission;
	}

	public void setEditPermission(Boolean editPermission) {
		this.editPermission = editPermission;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
}
