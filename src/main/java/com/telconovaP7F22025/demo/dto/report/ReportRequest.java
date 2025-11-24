package com.telconovaP7F22025.demo.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportRequest(
    @NotBlank(message = "El nombre del reporte es requerido")
    String nombreReporte,
    
    @NotNull(message = "Los filtros son requeridos")
    Object filtros,
    
    @NotNull(message = "Las métricas son requeridas")
    Object metricas,
    
    @NotNull(message = "El resumen es requerido")
    Object resumen
) {}
