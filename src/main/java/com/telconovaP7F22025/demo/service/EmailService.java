package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.model.Technician;

public interface EmailService {

    /**
     * Sends an assignment notification email to a technician
     * 
     * @param order      The work order being assigned
     * @param technician The technician receiving the assignment
     */
    void sendAssignmentEmail(Order order, Technician technician);

    /**
     * Sends a custom email
     * 
     * @param to          Recipient email address
     * @param subject     Email subject
     * @param htmlContent HTML content of the email
     */
    void sendEmail(String to, String subject, String htmlContent);
}
