package com.example.demo.services;

import com.example.demo.models.EmailHistory;
import com.example.demo.models.User;
import com.example.demo.repositories.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final EmailHistoryRepository emailHistoryRepository;
    
    @Autowired
    public EmailService(JavaMailSender mailSender, EmailHistoryRepository emailHistoryRepository) {
        this.mailSender = mailSender;
        this.emailHistoryRepository = emailHistoryRepository;
    }
    
    @Async
    public CompletableFuture<String> sendEmailWithRetry(User user, String toEmail, String subject, String body) {
        EmailHistory emailHistory = new EmailHistory(user, toEmail, subject, body);
        emailHistoryRepository.save(emailHistory);
        
        try {
            // Primeira tentativa
            sendEmailInternal(toEmail, subject, body);
            
            // Sucesso na primeira tentativa
            emailHistory.setStatus(EmailHistory.EmailStatus.SENT);
            emailHistory.setSentAt(LocalDateTime.now());
            emailHistoryRepository.save(emailHistory);
            
            return CompletableFuture.completedFuture("Email enviado com sucesso!");
            
        } catch (Exception e) {
            // Falha na primeira tentativa
            emailHistory.setStatus(EmailHistory.EmailStatus.FAILED);
            emailHistory.setErrorMessage(e.getMessage());
            emailHistoryRepository.save(emailHistory);
            
            // Retry após 5 segundos
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000); // 5 segundos de delay
                    
                    emailHistory.setStatus(EmailHistory.EmailStatus.RETRY);
                    emailHistory.setRetryCount(1);
                    emailHistoryRepository.save(emailHistory);
                    
                    sendEmailInternal(toEmail, subject, body);
                    
                    // Sucesso no retry
                    emailHistory.setStatus(EmailHistory.EmailStatus.SENT);
                    emailHistory.setSentAt(LocalDateTime.now());
                    emailHistoryRepository.save(emailHistory);
                    
                    return "Email enviado com sucesso no retry!";
                    
                } catch (Exception retryException) {
                    // Falha no retry
                    emailHistory.setStatus(EmailHistory.EmailStatus.FAILED);
                    emailHistory.setErrorMessage("Retry falhou: " + retryException.getMessage());
                    emailHistoryRepository.save(emailHistory);
                    
                    throw new RuntimeException("Falha no envio de email após retry: " + retryException.getMessage());
                }
            });
        }
    }
    
    private void sendEmailInternal(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    
    public String sendForgotPasswordEmail(User user) {
        String subject = "Recuperação de Senha";
        String body = createForgotPasswordTemplate(user.getName());
        
        try {
            sendEmailWithRetry(user, user.getEmail(), subject, body);
            return "Email de recuperação enviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao enviar email de recuperação: " + e.getMessage();
        }
    }
    
    public String sendWelcomeEmail(User user) {
        String subject = "Bem-vindo ao Sistema!";
        String body = createWelcomeTemplate(user.getName());
        
        try {
            sendEmailWithRetry(user, user.getEmail(), subject, body);
            return "Email de boas-vindas enviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao enviar email de boas-vindas: " + e.getMessage();
        }
    }
    
    private String createForgotPasswordTemplate(String userName) {
        return String.format("Olá %s,\n\n" +
                "Você solicitou a recuperação de sua senha.\n\n" +
                "Para redefinir sua senha, acesse o sistema e use a opção 'Redefinir Senha'.\n\n" +
                "Se você não solicitou esta recuperação, ignore este email.\n\n" +
                "Atenciosamente,\n" +
                "Equipe do Sistema", userName);
    }
    
    private String createWelcomeTemplate(String userName) {
        return String.format("Olá %s,\n\n" +
                "Seja bem-vindo ao nosso sistema!\n\n" +
                "Sua conta foi criada com sucesso e você já pode fazer login.\n\n" +
                "Em caso de dúvidas, entre em contato conosco.\n\n" +
                "Atenciosamente,\n" +
                "Equipe do Sistema", userName);
    }
    
    public String resendFailedEmail(Long emailHistoryId) {
        EmailHistory emailHistory = emailHistoryRepository.findById(emailHistoryId)
                .orElseThrow(() -> new RuntimeException("Histórico de email não encontrado"));
        
        if (emailHistory.getStatus() != EmailHistory.EmailStatus.FAILED) {
            return "Apenas emails com falha podem ser reenviados";
        }
        
        try {
            sendEmailWithRetry(emailHistory.getUser(), emailHistory.getToEmail(), 
                             emailHistory.getSubject(), emailHistory.getBody());
            return "Email reenviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao reenviar email: " + e.getMessage();
        }
    }
}
