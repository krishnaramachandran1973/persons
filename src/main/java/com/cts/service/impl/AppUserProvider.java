package com.cts.service.impl;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cts.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppUserProvider implements AuthenticationProvider {

	private final AuthenticationService authenticationService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Authenticating user {}", authentication.getName());
		String username = authentication.getName();
		UserDetails userDetails = authenticationService.loadUserByUsername(username);
		log.info("{}", userDetails.getAuthorities());
		if (bCryptPasswordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(username, authentication.getCredentials(),
					userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("User doesn't exist");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
