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
@Table(name = "phones")
public class Phone {
	private long id;
	private String type;
	private String number;
	private User user;
	
	public Phone() {
		
	}
	
	public Phone(String type, String number, User user) {
		this.type = type;
		this.number = number;
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
	
	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "number", nullable = false)
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
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
