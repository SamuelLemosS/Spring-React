package com.example.demo.services;

import com.example.demo.dto.ForgotPasswordDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                      JwtService jwtService,@Lazy AuthenticationManager authenticationManager,
                      EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }
    
    public String registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        
        userRepository.save(user);
        
        // Enviar email de boas-vindas
        emailService.sendWelcomeEmail(user);
        
        return "Usuário registrado com sucesso!";
    }
    
    public LoginResponseDto  login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            User user =  userRepository.findByEmail(loginDto.getEmail()).get();
            return new LoginResponseDto(token, user.getId());
            
        } catch (Exception e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
    
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByEmail(forgotPasswordDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        emailService.sendForgotPasswordEmail(user);
        
        return "Email de recuperação enviado para " + user.getEmail();
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            new ArrayList<>()
        );
    }
}
