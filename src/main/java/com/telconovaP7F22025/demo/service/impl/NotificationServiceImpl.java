package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.dto.notification.NotificationRequest;
import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.repository.OrderRepository;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.service.EmailService;
import com.telconovaP7F22025.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final OrderRepository orderRepository;
    private final TechnicianRepository technicianRepository;
    private final EmailService emailService;

    @Override
    public void sendNotification(NotificationRequest request) {
        Order order = orderRepository.findById(request.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        try {
            Long techId = Long.parseLong(request.idTecnico());
            Technician technician = technicianRepository.findById(techId)
                    .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

            for (String canal : request.canales()) {
                if ("email".equalsIgnoreCase(canal)) {
                    try {
                        log.info("📧 Enviando email a {} sobre orden {}", technician.getNameTecnico(), order.getId());
                        emailService.sendAssignmentEmail(order, technician);
                        log.info("✅ Email enviado exitosamente a {}", technician.getNameTecnico());
                    } catch (Exception e) {
                        log.error("❌ Error enviando email: {}", e.getMessage());
                        // No lanzamos excepción para que no falle la asignación
                    }
                } else if ("sms".equalsIgnoreCase(canal)) {
                    log.info("📱 SMS simulado enviado a {} sobre orden {}", technician.getNameTecnico(), order.getId());
                    // SMS sigue siendo simulado
                }
            }

            log.info("✅ Notificaciones procesadas para orden {} al técnico {}",
                    order.getId(), technician.getNameTecnico());

        } catch (NumberFormatException e) {
            throw new RuntimeException("ID de técnico inválido");
        }
    }
}
