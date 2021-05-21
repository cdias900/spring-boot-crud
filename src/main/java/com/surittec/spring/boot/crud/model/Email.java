package com.surittec.spring.boot.crud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "emails")
public class Email {
	private long id;
	private String email;
	private User user;
	
	public Email() {
		
	}
	
	public Email(String email, User user) {
		this.email = email;
		this.user = user;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
