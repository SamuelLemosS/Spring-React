package com.example.demo.controllers;

import com.example.demo.models.EmailHistory;
import com.example.demo.repositories.EmailHistoryRepository;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {
    
    private final EmailService emailService;
    private final EmailHistoryRepository emailHistoryRepository;
    
    @Autowired
    public EmailController(EmailService emailService, EmailHistoryRepository emailHistoryRepository) {
        this.emailService = emailService;
        this.emailHistoryRepository = emailHistoryRepository;
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<EmailHistory>> getEmailHistory() {
        List<EmailHistory> history = emailHistoryRepository.findAll();
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/history/failed")
    public ResponseEntity<List<EmailHistory>> getFailedEmails() {
        List<EmailHistory> failedEmails = emailHistoryRepository.findByStatus(EmailHistory.EmailStatus.FAILED);
        return ResponseEntity.ok(failedEmails);
    }
    
    @PostMapping("/resend/{id}")
    public ResponseEntity<String> resendFailedEmail(@PathVariable Long id) {
        try {
            String result = emailService.resendFailedEmail(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao reenviar: " + e.getMessage());
        }
    }
    
    @GetMapping("/stats")
    public ResponseEntity<String> getEmailStats() {
        long total = emailHistoryRepository.count();
        long sent = emailHistoryRepository.findByStatus(EmailHistory.EmailStatus.SENT).size();
        long failed = emailHistoryRepository.findByStatus(EmailHistory.EmailStatus.FAILED).size();
        long pending = emailHistoryRepository.findByStatus(EmailHistory.EmailStatus.PENDING).size();
        
        String stats = String.format("Estatísticas de Email:\n" +
                "Total: %d\n" +
                "Enviados: %d\n" +
                "Falharam: %d\n" +
                "Pendentes: %d", total, sent, failed, pending);
        
        return ResponseEntity.ok(stats);
    }
}
