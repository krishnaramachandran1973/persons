package com.cts.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PersonError {
	@Getter
	private String message;
}
