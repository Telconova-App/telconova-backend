package com.telconovaP7F22025.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import com.telconovaP7F22025.demo.dto.tech.CreateRequest;
import com.telconovaP7F22025.demo.model.Technician;
import jakarta.validation.Valid;


import com.telconovaP7F22025.demo.service.TechService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/technicians")
@RequiredArgsConstructor
@Tag(name = "Technician Controller", description = "Handles technician operations")
public class TechController {

    private final TechService techService;

    @PostMapping("/create")
    public ResponseEntity<String> createTechnician(@Valid @RequestBody CreateRequest createRequest) {
        boolean created = techService.createTechnician(createRequest);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Technician created");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Technician with that name already exists");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<java.util.List<Technician>> getAllTechnicians() {
        java.util.List<Technician> technicians = techService.getAllTechnicians();
        return ResponseEntity.ok(technicians);
    }
}