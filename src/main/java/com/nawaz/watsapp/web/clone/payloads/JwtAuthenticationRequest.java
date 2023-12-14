package com.nawaz.watsapp.web.clone.payloads;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {

    private String username;
    private String password;
}
