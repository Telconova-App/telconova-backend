package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.dto.assignment.AssignmentRequest;
import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.repository.OrderRepository;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final OrderRepository orderRepository;
    private final TechnicianRepository technicianRepository;

    @Override
    public Order assignManually(AssignmentRequest request) {
        Order order = orderRepository.findById(request.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        String previousTechId = order.getAssignedTo();
        String newTechId = request.idTecnico();

        // Update previous technician workload (decrement)
        if (previousTechId != null && !previousTechId.equals(newTechId)) {
            try {
                Long prevId = Long.parseLong(previousTechId);
                technicianRepository.findById(prevId).ifPresent(tech -> {
                    int workload = parseWorkload(tech.getWorkloadTecnico());
                    tech.setWorkloadTecnico(String.valueOf(Math.max(0, workload - 1)));
                    technicianRepository.save(tech);
                });
            } catch (NumberFormatException ignored) {}
        }

        // Update new technician workload (increment)
        if (newTechId != null && !newTechId.equals(previousTechId)) {
            try {
                Long newId = Long.parseLong(newTechId);
                technicianRepository.findById(newId).ifPresent(tech -> {
                    int workload = parseWorkload(tech.getWorkloadTecnico());
                    tech.setWorkloadTecnico(String.valueOf(workload + 1));
                    technicianRepository.save(tech);
                });
            } catch (NumberFormatException ignored) {}
        }

        // Update order
        order.setAssignedTo(newTechId);
        order.setAsignadoEn(LocalDateTime.now());
        order.setStatus(newTechId == null ? "pending" : "assigned");

        return orderRepository.save(order);
    }

    @Override
    public Order assignAutomatically(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Find best technician: same zone, lowest workload
        List<Technician> technicians = technicianRepository.findAll();
        
        Technician bestTech = technicians.stream()
                .filter(t -> t.getZoneTecnico().equals(order.getZona()))
                .filter(t -> t.getSpecialtyTecnico().equals(order.getServicio()))
                .min(Comparator.comparingInt(t -> parseWorkload(t.getWorkloadTecnico())))
                .orElse(null);

        // If no match with same specialty, try just same zone
        if (bestTech == null) {
            bestTech = technicians.stream()
                    .filter(t -> t.getZoneTecnico().equals(order.getZona()))
                    .min(Comparator.comparingInt(t -> parseWorkload(t.getWorkloadTecnico())))
                    .orElse(null);
        }

        // If still no match, get any available technician
        if (bestTech == null) {
            bestTech = technicians.stream()
                    .min(Comparator.comparingInt(t -> parseWorkload(t.getWorkloadTecnico())))
                    .orElseThrow(() -> new RuntimeException("No hay técnicos disponibles"));
        }

        // Assign using manual assignment logic
        AssignmentRequest request = new AssignmentRequest(
                orderId,
                String.valueOf(bestTech.getIdTecnico()),
                true
        );

        return assignManually(request);
    }

    private int parseWorkload(String workload) {
        try {
            return Integer.parseInt(workload);
        } catch (Exception e) {
            return 0;
        }
    }
}
