package com.nawaz.watsapp.web.clone.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
	private String token;
}
