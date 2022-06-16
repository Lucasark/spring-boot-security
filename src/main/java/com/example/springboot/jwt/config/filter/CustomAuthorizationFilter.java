package com.example.springboot.jwt.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springboot.jwt.config.UserDetailsServiceImpl;
import com.example.springboot.jwt.utils.JwtUtils;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JwtEncoder encoder;
	private final JwtDecoder decoder;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/login/signUp")) {
			filterChain.doFilter(request, response);
		} else if (request.getServletPath().equals("/login/refreshToken")) {
			refreshToken(request, response);
		} else {
			accessToken(request, response, filterChain);
		}
	}

	private void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String refreshToken = authorizationHeader.substring("Bearer ".length());
		String username = decoder.decode(refreshToken).getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		JwtClaimsSet accessClaims = JwtUtils.generateAccessClaims(userDetails);
		JwtClaimsSet refreshClaims = JwtUtils.generateRefreshClaims(userDetails);

		String newAccessToken = this.encoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();
		String newRefreshToken = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", newAccessToken);
		tokens.put("refresh_token", newRefreshToken);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
	
	private void accessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String tokenString = authorizationHeader.substring("Bearer ".length());
				Jwt token = this.decoder.decode(tokenString);
				String username = token.getSubject();

				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

				token.getClaimAsStringList("scope").forEach(scope -> {
					authorities.add(new SimpleGrantedAuthority(scope));
				});

				// Isso gera as authorities com SCOPE_{{ROLE}} nao apenas {{ROLE}}
				// Nao sei se existe uma solucao para isso
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				filterChain.doFilter(request, response);

			} catch (Exception exception) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			filterChain.doFilter(request, response);
		}
	}

}
