package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordDto {
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    // Construtores
    public ForgotPasswordDto() {}
    
    public ForgotPasswordDto(String email) {
        this.email = email;
    }
    
    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
