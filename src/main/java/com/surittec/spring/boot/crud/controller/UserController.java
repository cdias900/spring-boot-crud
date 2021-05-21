package com.surittec.spring.boot.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surittec.spring.boot.crud.Application;
import com.surittec.spring.boot.crud.exception.BadRequestException;
import com.surittec.spring.boot.crud.exception.ResourceNotFoundException;
import com.surittec.spring.boot.crud.model.User;
import com.surittec.spring.boot.crud.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping
	public User createUser(@Validated @RequestBody User user) throws BadRequestException {
		if(user.getEmails() == null || user.getEmails().size() == 0) throw new BadRequestException("Each user must have at least one email address");
		if(user.getPhones() == null || user.getPhones().size() == 0) throw new BadRequestException("Each user must have at least one phone number");
		user.getEmails().forEach(e -> e.setUser(user));
		user.getPhones().forEach(p -> p.setUser(user));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long userId, @Validated @RequestBody User user) throws ResourceNotFoundException, BadRequestException {
		User updatedUser = userRepository.findById(userId)
				.map(u -> {
					if(user.getEmails() != null && user.getEmails().size() > 0) {
						u.getEmails().clear();
						user.getEmails().forEach(e -> e.setUser(u));
						u.getEmails().addAll(user.getEmails());
					}
					if(user.getPhones() != null && user.getPhones().size() > 0) {
						u.getPhones().clear();
						user.getPhones().forEach(p -> p.setUser(u));
						u.getPhones().addAll(user.getPhones());
					}
					if(user.getName() != null && user.getName().length() > 0) u.setName(user.getName());
					if(user.getCpf() != null && user.getCpf().length() > 0) u.setCpf(user.getCpf());
					if(user.getCep() != null && user.getCep().length() > 0) u.setCep(user.getCep());
					if(user.getAddress() != null && user.getAddress().length() > 0) u.setAddress(user.getAddress());
					if(user.getCity() != null && user.getCity().length() > 0) u.setCity(user.getCity());
					if(user.getNeighborhood() != null && user.getNeighborhood().length() > 0) u.setNeighborhood(user.getNeighborhood());
					if(user.getState() != null && user.getState().length() > 0) u.setState(user.getState());
					u.setComplement(user.getComplement());
					return userRepository.save(u);
				})
				.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
		return ResponseEntity.ok().body(updatedUser);
	}
}
