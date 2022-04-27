package com.cts.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class JwtUtil {

	private Map<String, Object> claims = new HashMap<>();
	private static final String SECRET = "secret";

	public String generateToken(Authentication authentication) {
		claims.put("name", authentication.getName());
		claims.put("roles", authentication.getAuthorities());

		Instant issuedAt = Instant.now()
				.truncatedTo(ChronoUnit.SECONDS);
		Instant expiration = issuedAt.plus(1, ChronoUnit.HOURS);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(authentication.getName())
				.setIssuer("cts.com")
				.setIssuedAt(Date.from(issuedAt))
				.setExpiration(Date.from(expiration))
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

	public boolean validateJwtToken(String authToken) {
		try {
			log.info("Validating token");
			Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
			throw e;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			throw e;
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
			throw e;
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
			throw e;
		}
	}

}
