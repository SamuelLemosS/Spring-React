package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.EmailHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    
    @Mock
    private JavaMailSender mailSender;
    
    @Mock
    private EmailHistoryRepository emailHistoryRepository;
    
    @InjectMocks
    private EmailService emailService;
    
    @Test
    void testSendWelcomeEmail() {
        // Arrange
        User user = new User();
        user.setName("João Silva");
        user.setEmail("joao@exemplo.com");
        
        when(emailHistoryRepository.save(any(com.example.demo.models.EmailHistory.class)))
                .thenReturn(new com.example.demo.models.EmailHistory());
        
        // Act
        String result = emailService.sendWelcomeEmail(user);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("sucesso"));
        verify(emailHistoryRepository, times(2)).save(any(com.example.demo.models.EmailHistory.class));
    }
    
    @Test
    void testSendForgotPasswordEmail() {
        // Arrange
        User user = new User();
        user.setName("Maria Santos");
        user.setEmail("maria@exemplo.com");
        
        when(emailHistoryRepository.save(any(com.example.demo.models.EmailHistory.class)))
                .thenReturn(new com.example.demo.models.EmailHistory());
        
        // Act
        String result = emailService.sendForgotPasswordEmail(user);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("sucesso"));
        verify(emailHistoryRepository, times(2)).save(any(com.example.demo.models.EmailHistory.class));
    }
}
