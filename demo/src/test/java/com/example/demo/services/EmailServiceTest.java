package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.EmailHistory;
import com.example.demo.repositories.EmailHistoryRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailHistoryRepository emailHistoryRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    private User mockUser;

    @BeforeEach
    void setup() {
        mailSender = mock(JavaMailSender.class);
        emailHistoryRepository = mock(EmailHistoryRepository.class);
        userRepository = mock(UserRepository.class);
        emailService = new EmailService(mailSender, emailHistoryRepository, userRepository);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Samuel");
        mockUser.setEmail("teste@teste.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
    }

    @Test
    void deveEnviarEmailComSucesso() {
        CompletableFuture<String> future = emailService.sendEmailWithRetry(
                mockUser, "teste@teste.com", "Assunto", "Corpo"
        );

        String result = future.join(); // espera terminar async
        assertEquals("Email enviado com sucesso!", result);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailHistoryRepository, atLeastOnce()).save(any(EmailHistory.class));
    }

    @Test
    void deveFalharNoEnvioDeEmail() {
        doThrow(new RuntimeException("SMTP indisponível"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        CompletableFuture<String> future = emailService.sendEmailWithRetry(
                mockUser, "teste@teste.com", "Assunto", "Corpo"
        );

        RuntimeException ex = assertThrows(RuntimeException.class, future::join);
        assertTrue(ex.getMessage().contains("Falha no envio de email após retry"));

        verify(mailSender, atLeast(2)).send(any(SimpleMailMessage.class)); // tentou no retry
        verify(emailHistoryRepository, atLeastOnce()).save(any(EmailHistory.class));
    }
}
