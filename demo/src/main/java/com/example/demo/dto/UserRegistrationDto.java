package com.example.demo.dto;

import com.example.demo.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;

    private String role = "user";
    
    // Construtores
    public UserRegistrationDto() {}
    
    public UserRegistrationDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "user";
    }
    
    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User.UserRole getRole() { return "USER".equals(role) ? User.UserRole.USER : User.UserRole.ADMIN; }
    public void setRole(String role) { this.role = role; }
}
