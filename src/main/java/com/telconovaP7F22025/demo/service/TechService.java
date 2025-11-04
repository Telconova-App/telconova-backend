package com.telconovaP7F22025.demo.service;

import java.util.List;

import com.telconovaP7F22025.demo.dto.tech.CreateRequest;
import com.telconovaP7F22025.demo.model.Technician;



public interface TechService {

    boolean createTechnician(CreateRequest registerRequest);
    
    List<Technician> getAllTechnicians();
    
}
