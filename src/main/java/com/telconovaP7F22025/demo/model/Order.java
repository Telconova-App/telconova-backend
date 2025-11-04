package com.telconovaP7F22025.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
public class Order {

    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id; // e.g., O-1001

    @Column(name = "zona", nullable = false)
    private String zona;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "servicio", nullable = false)
    private String servicio;

    @Column(name = "descripcion", nullable = false, length = 2000)
    private String descripcion;

    @Column(name = "assigned_to")
    private String assignedTo; // technician id or code

    @Column(name = "status", nullable = false)
    private String status; // pending | assigned | completed | cancelled

    public Order() {}

    public Order(String id, String zona, LocalDateTime creadoEn, String servicio, String descripcion, String assignedTo, String status) {
        this.id = id;
        this.zona = zona;
        this.creadoEn = creadoEn;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
