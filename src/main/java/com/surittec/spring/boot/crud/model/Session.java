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

@Entity(name = "Sessions")
@Table(name = "sessions")
public class Session {
	private long id;
	private String token;
	private User user;
	
	public Session() {
		
	}
	
	public Session(String token, User user) {
		this.token = token;
		this.user = user;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}	
}
