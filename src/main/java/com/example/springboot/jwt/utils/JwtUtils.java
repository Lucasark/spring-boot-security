package com.example.springboot.jwt.utils;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public class JwtUtils {
    private static final long expiry = 36000L;
    private static final long expiryRefreshExtra = 36000L;
    
	public static JwtClaimsSet generateAccessClaims(Authentication authentication) {
		Instant now = Instant.now();
		
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        
		return JwtClaimsSet.builder()
				.issuer("self").issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
	}
	
	public static JwtClaimsSet generateAccessClaims(UserDetails userDetails) {
		Instant now = Instant.now();

		String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
		
		return JwtClaimsSet.builder()
				.issuer("self").issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();
	}
	
	public static JwtClaimsSet generateRefreshClaims(Authentication authentication) {
		Instant now = Instant.now();
		
		return JwtClaimsSet.builder()
				.issuer("self").issuedAt(now)
                .expiresAt(now.plusSeconds(expiry + expiryRefreshExtra))
                .subject(authentication.getName())
                .build();
	}
	
	public static JwtClaimsSet generateRefreshClaims(UserDetails userDetails) {
		Instant now = Instant.now();

		return JwtClaimsSet.builder()
				.issuer("self").issuedAt(now)
                .expiresAt(now.plusSeconds(expiry + expiryRefreshExtra))
                .subject(userDetails.getUsername())
                .build();
	}
}
