package com.frapee.JwtSecurityDemo.jwt;

import lombok.Getter;

@Getter
public class JwtResponse {
    private String token;
    private String user;
    private String type = "Bearer";

    public JwtResponse(String token, String user) {
        this.token = token;
        this.user = user;
    }
    
}
