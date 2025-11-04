package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.dto.tech.CreateRequest;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.service.TechService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService{

    private final TechnicianRepository technicianRepository;

    @Override
    @Transactional
    public boolean createTechnician(CreateRequest registerRequest) {
        String nameTecnico = registerRequest.nameTecnico();
        if (technicianRepository.existsByNameTecnico(nameTecnico)) {
            return false;
        }
        Technician t = new Technician();
        t.setNameTecnico(nameTecnico);
        t.setZoneTecnico(registerRequest.zoneTecnico());
        t.setWorkloadTecnico(registerRequest.workloadTecnico());
        t.setSpecialtyTecnico(registerRequest.specialtyTecnico());
        technicianRepository.save(t);
        return true;
    }

    @Transactional
    public java.util.List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }
    
}
