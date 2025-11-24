package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.dto.assignment.AssignmentRequest;

public interface AssignmentService {
    Order assignManually(AssignmentRequest request);
    Order assignAutomatically(String orderId);
}
