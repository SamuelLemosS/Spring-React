package com.example.demo.controllers;

import com.example.demo.models.EmailHistory;
import com.example.demo.models.User;
import com.example.demo.repositories.EmailHistoryRepository;
import com.example.demo.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public EmailController(EmailService emailService, EmailHistoryRepository emailHistoryRepository, UserRepository userRepository) {
        this.emailService = emailService;
        this.emailHistoryRepository = emailHistoryRepository;
        this.userRepository = userRepository;
    }

    private boolean isAdmin(Long userId) {
        User user = userRepository.findById(userId).get();

        return  user.getRole() == User.UserRole.ADMIN;
    }
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getEmailHistory(@PathVariable Long userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(403).body("Acesso negado: apenas admin pode acessar.");
        }
        List<EmailHistory> history = emailHistoryRepository.findAll();
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/history/failed/{userId}")
    public ResponseEntity<?> getFailedEmails(@PathVariable Long userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(403).body("Acesso negado: apenas admin pode acessar.");
        }
        List<EmailHistory> failedEmails = emailHistoryRepository.findByStatus(EmailHistory.EmailStatus.FAILED);
        return ResponseEntity.ok(failedEmails);
    }
    
    @PostMapping("/resend/{id}/{userId}")
    public ResponseEntity<String> resendFailedEmail(@PathVariable Long id,  @PathVariable Long userId) {
            if (!isAdmin(userId)) {
                return ResponseEntity.status(403).body("Acesso negado: apenas admin pode acessar.");
            }
            String result = emailService.resendFailedEmail(id);
            return ResponseEntity.ok(result);
    }
    
    @GetMapping("/stats/{userId}")
    public ResponseEntity<String> getEmailStats(@PathVariable Long userId) {
        if (!isAdmin(userId)) {
            return ResponseEntity.status(403).body("Acesso negado: apenas admin pode acessar.");
        }
        String result = emailService.sendStatsEmail(userId);
        
        return ResponseEntity.ok(result);
    }
}
