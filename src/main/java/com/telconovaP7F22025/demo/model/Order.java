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

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "direccion", length = 500)
    private String direccion;

    @Column(name = "prioridad")
    private String prioridad; // alta | media | baja

    @Column(name = "assigned_to")
    private String assignedTo; // technician id or code

    @Column(name = "asignado_en")
    private LocalDateTime asignadoEn;

    @Column(name = "asignado_por")
    private String asignadoPor;

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
        this.prioridad = "media"; // default
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

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public LocalDateTime getAsignadoEn() { return asignadoEn; }
    public void setAsignadoEn(LocalDateTime asignadoEn) { this.asignadoEn = asignadoEn; }

    public String getAsignadoPor() { return asignadoPor; }
    public void setAsignadoPor(String asignadoPor) { this.asignadoPor = asignadoPor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
