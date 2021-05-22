package com.surittec.spring.boot.crud.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surittec.spring.boot.crud.exception.BadRequestException;
import com.surittec.spring.boot.crud.exception.ResourceNotFoundException;
import com.surittec.spring.boot.crud.exception.UnauthorizedException;
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
	public Session login(@RequestBody Map<String, String> info) throws BadRequestException, ResourceNotFoundException, UnauthorizedException {
		String username = info.get("username");
		String password = info.get("password");
		if(username == null)
			throw new BadRequestException("Invalid username");
		if(password == null)
			throw new BadRequestException("Invalid password");
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new ResourceNotFoundException("Username not found");
		if(!passwordEncoder.matches(password, user.getPassword())) 
			throw new UnauthorizedException("Invalid password");
		List<Session> sessions = sessionRepository.findByUserId(user.getId());
		if(sessions != null && sessions.size() > 0)
			sessions.forEach(s -> sessionRepository.deleteById(s.getId()));			
		Session session = new Session();
		session.setToken(UUID.randomUUID().toString());
		session.setUser(user);
		return sessionRepository.save(session);
	}
	
	@PostMapping("/logout")
	public void logout(@RequestHeader(name = "Authorization", required = true) String token) {
		Session session = sessionRepository.findByToken(token);
		if(session == null)
			return;
		sessionRepository.deleteById(session.getId());
		return;
	}
}
