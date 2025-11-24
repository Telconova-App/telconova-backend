package com.telconovaP7F22025.demo.dto.assignment;

import jakarta.validation.constraints.NotBlank;

public record AssignmentRequest(
    @NotBlank(message = "El ID de la orden es requerido")
    String idOrden,
    
    String idTecnico, // Opcional para asignación manual
    
    Boolean automatico // true para asignación automática
) {}
