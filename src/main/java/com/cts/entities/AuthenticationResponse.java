package com.cts.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class AuthenticationResponse {
	@Getter
	private final String jwt;

}
