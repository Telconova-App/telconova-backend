package com.telconovaP7F22025.demo.repository;
import com.telconovaP7F22025.demo.model.Technician;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    Optional<Technician> findByNameTecnico(String nameTecnico);
    boolean existsByNameTecnico(String nameTecnico);

}
