package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> testEmail(@RequestBody Map<String, String> request) {
        try {
            String to = request.get("to");

            log.info("Enviando email de prueba a: {}", to);

            emailService.sendEmail(
                    to,
                    "Test Email from TelcoNova",
                    "<h1>Hello from TelcoNova!</h1><p>This is a test email from Resend.</p>");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Email enviado exitosamente a " + to);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error enviando email de prueba: {}", e.getMessage(), e);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}
