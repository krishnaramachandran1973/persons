package com.cts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entities.AppUser;
import com.cts.entities.AuthenticationResponse;
import com.cts.service.impl.AppUserProvider;
import com.cts.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

	private final AppUserProvider appUserProvider;
	private final JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AppUser appUser) {
		log.info("Logging in user {}", appUser);
		Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(appUser.getUsername(),
				appUser.getPassword());
		try {
			Authentication authenticatedUser = appUserProvider.authenticate(toAuthenticate);
			AuthenticationResponse jwt = AuthenticationResponse.builder()
					.jwt(jwtUtil.generateTokeb(authenticatedUser))
					.build();
			return ResponseEntity.ok(jwt);
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
}
