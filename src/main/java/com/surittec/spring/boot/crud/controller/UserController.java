package com.surittec.spring.boot.crud.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surittec.spring.boot.crud.exception.BadRequestException;
import com.surittec.spring.boot.crud.exception.ResourceNotFoundException;
import com.surittec.spring.boot.crud.exception.UnauthorizedException;
import com.surittec.spring.boot.crud.model.Email;
import com.surittec.spring.boot.crud.model.Session;
import com.surittec.spring.boot.crud.model.User;
import com.surittec.spring.boot.crud.repository.SessionRepository;
import com.surittec.spring.boot.crud.repository.UserRepository;
import com.surittec.spring.boot.crud.utils.Utils;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}*/
	
	@PostMapping
	public User createUser(@Validated @RequestBody User user) throws BadRequestException {
		if(user.getEmails() == null || user.getEmails().size() == 0)
			throw new BadRequestException("Each user must have at least one email address");
		if(user.getPhones() == null || user.getPhones().size() == 0)
			throw new BadRequestException("Each user must have at least one phone number");
		if(user.getName().length() < 3 || user.getName().length() > 100)
			throw new BadRequestException("The name must have between 3 and 100 characters");
		if(!Utils.isNameValid(user.getName()))
			throw new BadRequestException("Invalid name");
		for(Email email : user.getEmails()) {
			if(!Utils.isEmailValid(email.getEmail()))
				throw new BadRequestException("Invalid email");
			email.setUser(user);
		}
		user.getPhones().forEach(p -> p.setUser(user));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(value = "id") Long userId
		) throws ResourceNotFoundException, UnauthorizedException {
		Session session = sessionRepository.findByToken(token);
		if(session == null)
			throw new UnauthorizedException("Invalid session");
		if(session.getUser().getId() != userId)
			throw new UnauthorizedException("Unauthorized");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUserById(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(value = "id") Long userId,
			@Validated @RequestBody User user
		) throws ResourceNotFoundException, BadRequestException, UnauthorizedException {
		Session session = sessionRepository.findByToken(token);
		if(session == null)
			throw new UnauthorizedException("Invalid session");
		if(session.getUser().getId() != userId)
			throw new UnauthorizedException("Unauthorized");
		if(!session.getUser().getEditPermission())
			throw new UnauthorizedException("Insufficient permissions");
		if(user.getName().length() < 3 || user.getName().length() > 100)
			throw new BadRequestException("The name must have between 3 and 100 characters");
		if(!Utils.isNameValid(user.getName()))
			throw new BadRequestException("Invalid name");
		for(Email email : user.getEmails())
			if(!Utils.isEmailValid(email.getEmail()))
				throw new BadRequestException("Invalid email");
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
					if(user.getName() != null && user.getName().length() > 0)
						u.setName(user.getName());
					if(user.getCpf() != null && user.getCpf().length() > 0)
						u.setCpf(user.getCpf());
					if(user.getCep() != null && user.getCep().length() > 0)
						u.setCep(user.getCep());
					if(user.getAddress() != null && user.getAddress().length() > 0)
						u.setAddress(user.getAddress());
					if(user.getCity() != null && user.getCity().length() > 0)
						u.setCity(user.getCity());
					if(user.getNeighborhood() != null && user.getNeighborhood().length() > 0)
						u.setNeighborhood(user.getNeighborhood());
					if(user.getState() != null && user.getState().length() > 0)
						u.setState(user.getState());
					u.setComplement(user.getComplement());
					return userRepository.save(u);
				})
				.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
		return ResponseEntity.ok().body(updatedUser);
	}
}
