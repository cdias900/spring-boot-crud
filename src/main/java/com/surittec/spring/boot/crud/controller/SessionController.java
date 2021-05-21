package com.surittec.spring.boot.crud.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surittec.spring.boot.crud.Application;
import com.surittec.spring.boot.crud.exception.BadRequestException;
import com.surittec.spring.boot.crud.exception.ResourceNotFoundException;
import com.surittec.spring.boot.crud.model.Session;
import com.surittec.spring.boot.crud.model.User;
import com.surittec.spring.boot.crud.repository.SessionRepository;
import com.surittec.spring.boot.crud.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class SessionController {
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public Session login(@RequestBody Map<String, String> info) throws BadRequestException, ResourceNotFoundException {
		String username = info.get("username");
		String password = info.get("password");
		if(username == null) throw new BadRequestException("Invalid username");
		if(password == null) throw new BadRequestException("Invalid password");
		User user = userRepository.findByUsername(username);
		if(user == null) throw new ResourceNotFoundException("Username not found");
		if(!passwordEncoder.matches(password, user.getPassword())) throw new BadRequestException("Invalid password");
		Session session = sessionRepository.findByUserId(user.getId());
		if(session != null) sessionRepository.deleteById(session.getId());
		session = new Session();
		session.setToken(UUID.randomUUID().toString());
		session.setUser(user);
		return sessionRepository.save(session);
	}
}
