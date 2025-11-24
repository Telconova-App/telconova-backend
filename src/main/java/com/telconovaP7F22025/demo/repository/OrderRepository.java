package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByAssignedTo(String assignedTo);
    List<Order> findByStatus(String status);
    List<Order> findByZona(String zona);
    List<Order> findByStatusAndZona(String status, String zona);
}

