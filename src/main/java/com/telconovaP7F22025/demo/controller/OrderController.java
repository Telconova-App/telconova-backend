package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.repository.OrderRepository;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.model.Technician;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = { "${FRONTEND_URL:http://localhost:5173}", "http://localhost:5173",
        "http://localhost:8081" }, allowCredentials = "true")
public class OrderController {

    private final OrderRepository orderRepository;
    private final TechnicianRepository technicianRepository;

    public OrderController(OrderRepository orderRepository, TechnicianRepository technicianRepository) {
        this.orderRepository = orderRepository;
        this.technicianRepository = technicianRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String zona) {
        List<Order> orders;

        if (status != null && zona != null) {
            orders = orderRepository.findByStatusAndZona(status, zona);
        } else if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else if (zona != null) {
            orders = orderRepository.findByZona(zona);
        } else {
            orders = orderRepository.findAll();
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Map<String, String> body) {
        String id = body.getOrDefault("id", "O-" + System.currentTimeMillis());
        String zona = body.getOrDefault("zona", "zona centro");
        String servicio = body.getOrDefault("servicio", "Servicio");
        String descripcion = body.getOrDefault("descripcion", "");
        String assignedTo = body.getOrDefault("assignedTo", null);
        String status = body.getOrDefault("status", assignedTo == null ? "pending" : "assigned");

        Order order = new Order(
                id,
                zona,
                LocalDateTime.now(),
                servicio,
                descripcion,
                assignedTo,
                status);

        Order saved = orderRepository.save(order);
        // If created already assigned, increment technician workload once
        if (assignedTo != null) {
            try {
                Long techId = Long.parseLong(assignedTo);
                technicianRepository.findById(techId).ifPresent(t -> {
                    int wl = safeParse(t.getWorkloadTecnico());
                    t.setWorkloadTecnico(Integer.toString(wl + 1));
                    technicianRepository.save(t);
                });
            } catch (NumberFormatException ignored) {
            }
        }
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody Map<String, String> updates) {
        Optional<Order> existingOpt = orderRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Order existing = existingOpt.get();
        if (updates.containsKey("zona"))
            existing.setZona(updates.get("zona"));
        if (updates.containsKey("servicio"))
            existing.setServicio(updates.get("servicio"));
        if (updates.containsKey("descripcion"))
            existing.setDescripcion(updates.get("descripcion"));
        if (updates.containsKey("assignedTo"))
            existing.setAssignedTo(updates.get("assignedTo"));
        if (updates.containsKey("status"))
            existing.setStatus(updates.get("status"));
        Order saved = orderRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Order> assign(@RequestBody Map<String, String> body) {
        String orderId = body.get("orderId");
        String technicianId = body.get("technicianId");
        String previousTechnicianId = body.get("previousTechnicianId");
        Optional<Order> existingOpt = orderRepository.findById(orderId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Order existing = existingOpt.get();

        String currentAssigned = existing.getAssignedTo();
        boolean isChange = (technicianId == null && currentAssigned != null)
                || (technicianId != null && !technicianId.equals(currentAssigned));

        // If changing away from current technician, decrement previous workload
        if (isChange && currentAssigned != null) {
            try {
                Long prevId = Long.parseLong(currentAssigned);
                technicianRepository.findById(prevId).ifPresent(prev -> {
                    int wl = safeParse(prev.getWorkloadTecnico());
                    wl = Math.max(0, wl - 1);
                    prev.setWorkloadTecnico(Integer.toString(wl));
                    technicianRepository.save(prev);
                });
            } catch (NumberFormatException ignored) {
            }
        }

        // Increment new technician workload only if changed to a new technician (not
        // same)
        if (isChange && technicianId != null) {
            try {
                Long newId = Long.parseLong(technicianId);
                technicianRepository.findById(newId).ifPresent(next -> {
                    int wl = safeParse(next.getWorkloadTecnico());
                    wl = wl + 1;
                    next.setWorkloadTecnico(Integer.toString(wl));
                    technicianRepository.save(next);
                });
            } catch (NumberFormatException ignored) {
            }
        }

        existing.setAssignedTo(technicianId);
        existing.setStatus(technicianId == null ? "pending" : "assigned");
        Order saved = orderRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    private int safeParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    @PostMapping("/recalculate-workloads")
    public ResponseEntity<Map<String, Integer>> recalcWorkloads() {
        // Recalculate workload as the number of orders assigned to each technician
        Map<String, Integer> result = new java.util.HashMap<>();
        technicianRepository.findAll().forEach(tech -> {
            String techId = String.valueOf(tech.getIdTecnico());
            int count = (int) orderRepository.findByAssignedTo(techId).size();
            tech.setWorkloadTecnico(Integer.toString(count));
            technicianRepository.save(tech);
            result.put(tech.getNameTecnico(), count);
        });
        return ResponseEntity.ok(result);
    }
}
