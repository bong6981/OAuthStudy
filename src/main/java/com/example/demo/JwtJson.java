package com.example.demo;

public class JwtJson {
    private final String jwt;

    public JwtJson(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
