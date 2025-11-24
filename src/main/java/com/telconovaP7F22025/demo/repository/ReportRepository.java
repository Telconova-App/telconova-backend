package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByCreadoPor(String creadoPor);
    Page<Report> findByCreadoPor(String creadoPor, Pageable pageable);
    Page<Report> findAll(Pageable pageable);
}
