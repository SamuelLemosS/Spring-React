package com.example.demo.dto;

public class LoginResponseDto {
    private String token;
    private Long userId;

    public LoginResponseDto(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}

