package com.example.springboot.jwt.controller;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.jwt.entity.Customer;
import com.example.springboot.jwt.entity.Role;
import com.example.springboot.jwt.entity.enums.Roles;
import com.example.springboot.jwt.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * The auth controller to handle login requests
 *
 * @author imesha
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CrossOrigin(origins = { "${app.security.cors.origin}" })
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository repository;
	

	@PostMapping(path = "/signUp", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> signUp(@RequestParam String username, @RequestParam String role,
			@RequestParam String password) {

		Customer customer = Customer.builder().password(passwordEncoder.encode(password)).username(username)
				.roles(Set.of(Role.builder().name(Roles.valueOf(role)).build())).enable(true).build();

		repository.save(customer);

		return ResponseEntity.ok("User registered successfully!");
	}
}
