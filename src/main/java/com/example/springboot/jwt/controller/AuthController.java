package com.example.springboot.jwt.controller;

import com.example.springboot.jwt.entity.Customer;
import com.example.springboot.jwt.entity.Role;
import com.example.springboot.jwt.entity.enums.Roles;
import com.example.springboot.jwt.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

/**
 * The auth controller to handle login requests
 *
 * @author imesha
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CrossOrigin(origins = {"${app.security.cors.origin}"})
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository repository;

    @PostMapping(path = "/signUp", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> signUp(
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam String password) {

        Customer customer = Customer.builder()
                .password(passwordEncoder.encode(password))
                .username(username)
                .roles(Set.of(Role.builder()
                        .name(Roles.valueOf(role))
                        .build()))
                .enable(true)
                .build();

        repository.save(customer);

        return ResponseEntity.ok("User registered successfully!");
    }
}
