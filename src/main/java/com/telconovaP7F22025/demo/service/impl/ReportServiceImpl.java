package com.telconovaP7F22025.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telconovaP7F22025.demo.dto.report.ReportRequest;
import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.model.Report;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.repository.OrderRepository;
import com.telconovaP7F22025.demo.repository.ReportRepository;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final OrderRepository orderRepository;
    private final TechnicianRepository technicianRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> getTechnicianMetrics(String startDate, String endDate, String serviceType, String zone) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);

        List<Order> allOrders = orderRepository.findAll();
        List<Technician> allTechnicians = technicianRepository.findAll();

        // Filter orders by date range
        List<Order> filteredOrders = allOrders.stream()
                .filter(o -> o.getCreadoEn().isAfter(start) && o.getCreadoEn().isBefore(end))
                .collect(Collectors.toList());

        // Apply service type filter
        if (serviceType != null && !serviceType.equals("all")) {
            filteredOrders = filteredOrders.stream()
                    .filter(o -> serviceType.equals(o.getServicio()))
                    .collect(Collectors.toList());
        }

        // Apply zone filter
        if (zone != null && !zone.equals("all")) {
            filteredOrders = filteredOrders.stream()
                    .filter(o -> zone.equals(o.getZona()))
                    .collect(Collectors.toList());
        }

        // Calculate metrics per technician
        List<Map<String, Object>> metrics = new ArrayList<>();
        int totalOrders = 0;
        int totalCompleted = 0;
        int totalInProgress = 0;
        double totalResolutionTime = 0;
        int completedCount = 0;

        for (Technician tech : allTechnicians) {
            String techId = String.valueOf(tech.getIdTecnico());
            
            List<Order> techOrders = filteredOrders.stream()
                    .filter(o -> techId.equals(o.getAssignedTo()))
                    .collect(Collectors.toList());

            if (techOrders.isEmpty()) continue;

            long completed = techOrders.stream().filter(o -> "completed".equals(o.getStatus())).count();
            long inProgress = techOrders.stream().filter(o -> "assigned".equals(o.getStatus()) || "in_progress".equals(o.getStatus())).count();

            // Calculate average resolution time for completed orders
            double avgResolutionTime = techOrders.stream()
                    .filter(o -> "completed".equals(o.getStatus()) && o.getAsignadoEn() != null)
                    .mapToDouble(o -> {
                        Duration duration = Duration.between(o.getCreadoEn(), LocalDateTime.now());
                        return duration.toDays();
                    })
                    .average()
                    .orElse(0.0);

            Map<String, Object> techMetric = new HashMap<>();
            techMetric.put("technicianId", techId);
            techMetric.put("technicianName", tech.getNameTecnico());
            techMetric.put("zone", tech.getZoneTecnico());
            techMetric.put("specialty", tech.getSpecialtyTecnico());
            techMetric.put("totalOrders", techOrders.size());
            techMetric.put("completedOrders", completed);
            techMetric.put("inProgressOrders", inProgress);
            techMetric.put("avgResolutionTime", Math.round(avgResolutionTime * 10.0) / 10.0);

            metrics.add(techMetric);

            totalOrders += techOrders.size();
            totalCompleted += completed;
            totalInProgress += inProgress;
            if (avgResolutionTime > 0) {
                totalResolutionTime += avgResolutionTime;
                completedCount++;
            }
        }

        // Calculate summary
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", totalOrders);
        summary.put("totalCompleted", totalCompleted);
        summary.put("totalInProgress", totalInProgress);
        summary.put("avgResolutionTime", completedCount > 0 ? Math.round((totalResolutionTime / completedCount) * 10.0) / 10.0 : 0.0);

        Map<String, Object> result = new HashMap<>();
        result.put("metrics", metrics);
        result.put("summary", summary);

        return result;
    }

    @Override
    public Report saveReport(ReportRequest request, String userId) {
        try {
            String reportId = "RPT-" + System.currentTimeMillis();
            
            String filtrosJson = objectMapper.writeValueAsString(request.filtros());
            String metricasJson = objectMapper.writeValueAsString(request.metricas());
            String resumenJson = objectMapper.writeValueAsString(request.resumen());

            Report report = new Report(
                    reportId,
                    request.nombreReporte(),
                    filtrosJson,
                    metricasJson,
                    resumenJson,
                    userId
            );

            return reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el reporte: " + e.getMessage());
        }
    }

    @Override
    public Page<Report> getReportHistory(int page, int limit, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(direction, sortBy));
        return reportRepository.findAll(pageable);
    }

    @Override
    public Report getReportDetail(String reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    @Override
    public void deleteReport(String reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new RuntimeException("Reporte no encontrado");
        }
        reportRepository.deleteById(reportId);
    }
}
