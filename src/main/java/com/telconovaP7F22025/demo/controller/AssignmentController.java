package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.dto.assignment.AssignmentRequest;
import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.service.AssignmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Tag(name = "Assignment Controller", description = "Handles work order assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/manual")
    public ResponseEntity<Order> assignManually(@Valid @RequestBody AssignmentRequest request) {
        try {
            Order assignedOrder = assignmentService.assignManually(request);
            return ResponseEntity.ok(assignedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/automatic")
    public ResponseEntity<Order> assignAutomatically(@RequestBody java.util.Map<String, String> body) {
        try {
            String orderId = body.get("idOrden");
            if (orderId == null) {
                orderId = body.get("orderId"); // Fallback for English naming
            }
            
            if (orderId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Order assignedOrder = assignmentService.assignAutomatically(orderId);
            return ResponseEntity.ok(assignedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
