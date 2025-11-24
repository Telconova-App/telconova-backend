package com.telconovaP7F22025.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "reportes")
public class Report {
    
    @Id
    @Column(name = "id_reporte", nullable = false, length = 64)
    private String idReporte;

    @Column(name = "nombre_reporte", nullable = false)
    private String nombreReporte;

    @Column(name = "filtros", columnDefinition = "TEXT")
    private String filtros; // JSON string

    @Column(name = "metricas", columnDefinition = "TEXT")
    private String metricas; // JSON string

    @Column(name = "resumen", columnDefinition = "TEXT")
    private String resumen; // JSON string

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    public Report() {
        this.creadoEn = LocalDateTime.now();
    }

    public Report(String idReporte, String nombreReporte, String filtros, String metricas, String resumen, String creadoPor) {
        this.idReporte = idReporte;
        this.nombreReporte = nombreReporte;
        this.filtros = filtros;
        this.metricas = metricas;
        this.resumen = resumen;
        this.creadoPor = creadoPor;
        this.creadoEn = LocalDateTime.now();
    }
}
