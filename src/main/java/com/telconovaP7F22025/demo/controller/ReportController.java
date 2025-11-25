package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.dto.report.ReportRequest;
import com.telconovaP7F22025.demo.model.Report;
import com.telconovaP7F22025.demo.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = { "${FRONTEND_URL:http://localhost:5173}", "http://localhost:5173",
        "http://localhost:8081" }, allowCredentials = "true")
@Tag(name = "Report Controller", description = "Handles report generation and management")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/technician-metrics")
    public ResponseEntity<Map<String, Object>> getTechnicianMetrics(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false, defaultValue = "all") String serviceType,
            @RequestParam(required = false, defaultValue = "all") String zone) {
        try {
            Map<String, Object> data = reportService.getTechnicianMetrics(startDate, endDate, serviceType, zone);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveReport(
            @Valid @RequestBody ReportRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Extract user from token (simplified - in production use proper JWT parsing)
            String userId = "user-001"; // Default user

            Report savedReport = reportService.saveReport(request, userId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Reporte guardado exitosamente");
            response.put("data", savedReport);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getReportHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "creadoEn") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        try {
            Page<Report> reportPage = reportService.getReportHistory(page, limit, sortBy, sortOrder);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", reportPage.getNumber() + 1);
            pagination.put("totalPages", reportPage.getTotalPages());
            pagination.put("totalReports", reportPage.getTotalElements());
            pagination.put("limit", limit);

            Map<String, Object> data = new HashMap<>();
            data.put("reports", reportPage.getContent());
            data.put("pagination", pagination);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/history/{reportId}")
    public ResponseEntity<Map<String, Object>> getReportDetail(@PathVariable String reportId) {
        try {
            Report report = reportService.getReportDetail(reportId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/history/{reportId}")
    public ResponseEntity<Map<String, Object>> deleteReport(@PathVariable String reportId) {
        try {
            reportService.deleteReport(reportId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Reporte eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
