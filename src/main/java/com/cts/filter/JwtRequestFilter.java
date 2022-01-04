package com.cts.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.service.AuthenticationService;
import com.cts.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final AuthenticationService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			log.info("User has submitted a token");
			String token = authHeader.substring(7);
			String username = jwtUtil.extractUsername(token);
			log.info("Extracted usename from token is {}", username);
			if (username != null) {
				if (SecurityContextHolder.getContext()
						.getAuthentication() == null) {
					UserDetails userDetails = authenticationService.loadUserByUsername(username);
					Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
							null, userDetails.getAuthorities());
					log.info("Populating user in the SecurityContext");
					SecurityContextHolder.getContext()
							.setAuthentication(authentication);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
