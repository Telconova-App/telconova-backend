package com.telconovaP7F22025.demo.dto.tech;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String nameTecnico, @NotBlank String zoneTecnico, @NotBlank String workloadTecnico, @NotBlank String specialtyTecnico) {
    
}
