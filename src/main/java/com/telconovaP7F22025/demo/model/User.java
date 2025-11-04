package com.telconovaP7F22025.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "usuarios")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(name = "emailUsuario", nullable = false, unique = true)
    private String emailUsuario;

    @Column(name = "passwordUsuario", nullable = false)
    private String passwordUsuario;
    
    @Column(name = "nameUsuario", nullable = false)
    private String name;

    @Column(name = "roleUsuario", nullable = false)
    private String role;

    // Additional getters needed for Spring Security
    public Long getId() {
        return idUsuario;
    }

    public String getEmail() {
        return emailUsuario;
    }

    public String getPassword() {
        return passwordUsuario;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return emailUsuario;
    }
}
