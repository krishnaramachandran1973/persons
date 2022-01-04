package com.cts.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtUtil {

	private Map<String, Object> claims = new HashMap<>();
	private static final String SECRET = "secret";

	public String generateTokeb(Authentication authentication) {
		claims.put("name", authentication.getName());
		claims.put("roles", authentication.getAuthorities());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(authentication.getName())
				.setIssuer("cts.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

}
