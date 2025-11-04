package com.telconovaP7F22025.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Entity @Table(name = "tecnicos")
public class Technician {
    
    @Id @Column(name = "id_tecnico") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTecnico;

    @Column(name = "name_tecnico", nullable = false)
    private String nameTecnico;

    @Column(name = "zone_tecnico", nullable = false)
    private String zoneTecnico;

    @Column(name = "workload_tecnico", nullable = false)
    private String workloadTecnico;

    @Column(name = "speciality_tecnico", nullable = false)
    private String specialtyTecnico;
}
