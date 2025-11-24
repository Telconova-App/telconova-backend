package com.telconovaP7F22025.demo.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record NotificationRequest(
    @NotBlank(message = "El ID de la orden es requerido")
    String idOrden,
    
    @NotBlank(message = "El ID del técnico es requerido")
    String idTecnico,
    
    @NotEmpty(message = "Debe especificar al menos un canal de notificación")
    List<String> canales // "email", "sms"
) {}
