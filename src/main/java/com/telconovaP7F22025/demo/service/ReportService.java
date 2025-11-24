package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.model.Report;
import com.telconovaP7F22025.demo.dto.report.ReportRequest;
import org.springframework.data.domain.Page;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getTechnicianMetrics(String startDate, String endDate, String serviceType, String zone);
    Report saveReport(ReportRequest request, String userId);
    Page<Report> getReportHistory(int page, int limit, String sortBy, String sortOrder);
    Report getReportDetail(String reportId);
    void deleteReport(String reportId);
}
