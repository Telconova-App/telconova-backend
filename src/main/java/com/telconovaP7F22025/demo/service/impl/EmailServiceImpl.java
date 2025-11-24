package com.telconovaP7F22025.demo.service.impl;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.telconovaP7F22025.demo.model.Order;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final String fromEmail;
    private final String fromName;

    public EmailServiceImpl(
            @Value("${resend.api.key}") String apiKey,
            @Value("${resend.from.email}") String fromEmail,
            @Value("${resend.from.name}") String fromName) {
        this.resend = new Resend(apiKey);
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        log.info("EmailService initialized with from: {} <{}>", fromName, fromEmail);
    }

    @Override
    public void sendAssignmentEmail(Order order, Technician technician) {
        try {
            String technicianEmail = technician.getEmailTecnico();

            if (technicianEmail == null || technicianEmail.trim().isEmpty()) {
                log.warn("Técnico {} no tiene email configurado", technician.getNameTecnico());
                return;
            }

            String subject = "Nueva Orden Asignada - " + order.getId();
            String htmlContent = buildAssignmentEmailHtml(order, technician);

            sendEmail(technicianEmail, subject, htmlContent);

            log.info("Email de asignación enviado exitosamente a {} ({})",
                    technician.getNameTecnico(), technicianEmail);

        } catch (Exception e) {
            log.error("Error enviando email de asignación: {}", e.getMessage(), e);
            // No lanzamos excepción para que no falle la asignación
        }
    }

    @Override
    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromName + " <" + fromEmail + ">")
                    .to(to)
                    .subject(subject)
                    .html(htmlContent)
                    .build();

            CreateEmailResponse response = resend.emails().send(params);

            log.info("Email enviado exitosamente. ID: {}", response.getId());

        } catch (ResendException e) {
            log.error("Error de Resend al enviar email: {}", e.getMessage(), e);
            throw new RuntimeException("Error enviando email: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al enviar email: {}", e.getMessage(), e);
            throw new RuntimeException("Error enviando email: " + e.getMessage(), e);
        }
    }

    private String buildAssignmentEmailHtml(Order order, Technician technician) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = order.getCreadoEn().format(formatter);

        String priorityColor = switch (order.getPrioridad().toLowerCase()) {
            case "high", "alta" -> "#ef4444";
            case "medium", "media" -> "#f59e0b";
            case "low", "baja" -> "#10b981";
            default -> "#6b7280";
        };

        String priorityText = switch (order.getPrioridad().toLowerCase()) {
            case "high" -> "Alta";
            case "medium" -> "Media";
            case "low" -> "Baja";
            default -> order.getPrioridad();
        };

        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Nueva Orden Asignada</title>
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f3f4f6;">
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f3f4f6; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #2563eb 0%%, #1d4ed8 100%%); padding: 30px 40px; text-align: center;">
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">
                                                📋 Nueva Orden Asignada
                                            </h1>
                                        </td>
                                    </tr>

                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 40px;">
                                            <p style="margin: 0 0 20px 0; font-size: 16px; color: #374151; line-height: 1.6;">
                                                Hola <strong>%s</strong>,
                                            </p>
                                            <p style="margin: 0 0 30px 0; font-size: 16px; color: #374151; line-height: 1.6;">
                                                Se te ha asignado una nueva orden de trabajo. A continuación encontrarás los detalles:
                                            </p>

                                            <!-- Order Details Card -->
                                            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f9fafb; border-radius: 6px; border: 1px solid #e5e7eb; margin-bottom: 30px;">
                                                <tr>
                                                    <td style="padding: 20px;">
                                                        <h2 style="margin: 0 0 20px 0; color: #111827; font-size: 18px; font-weight: 600; border-bottom: 2px solid #e5e7eb; padding-bottom: 10px;">
                                                            Detalles de la Orden
                                                        </h2>

                                                        <table width="100%%" cellpadding="8" cellspacing="0">
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500; width: 140px;">ID de Orden:</td>
                                                                <td style="color: #111827; font-size: 14px; font-weight: 600;">%s</td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Cliente:</td>
                                                                <td style="color: #111827; font-size: 14px;">%s</td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Dirección:</td>
                                                                <td style="color: #111827; font-size: 14px;">%s</td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Zona:</td>
                                                                <td style="color: #111827; font-size: 14px;">%s</td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Servicio:</td>
                                                                <td style="color: #111827; font-size: 14px;">%s</td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Prioridad:</td>
                                                                <td>
                                                                    <span style="display: inline-block; padding: 4px 12px; border-radius: 12px; font-size: 12px; font-weight: 600; color: #ffffff; background-color: %s;">
                                                                        %s
                                                                    </span>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="color: #6b7280; font-size: 14px; font-weight: 500;">Fecha Creación:</td>
                                                                <td style="color: #111827; font-size: 14px;">%s</td>
                                                            </tr>
                                                        </table>

                                                        <div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #e5e7eb;">
                                                            <p style="margin: 0 0 8px 0; color: #6b7280; font-size: 14px; font-weight: 500;">Descripción:</p>
                                                            <p style="margin: 0; color: #111827; font-size: 14px; line-height: 1.6;">
                                                                %s
                                                            </p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>

                                            <!-- Action Button -->
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td align="center" style="padding: 20px 0;">
                                                        <a href="#" style="display: inline-block; padding: 14px 32px; background-color: #2563eb; color: #ffffff; text-decoration: none; border-radius: 6px; font-size: 16px; font-weight: 600; box-shadow: 0 2px 4px rgba(37, 99, 235, 0.3);">
                                                            Ver en el Sistema
                                                        </a>
                                                    </td>
                                                </tr>
                                            </table>

                                            <p style="margin: 30px 0 0 0; font-size: 14px; color: #6b7280; line-height: 1.6;">
                                                Por favor, revisa los detalles y procede con la orden lo antes posible.
                                            </p>
                                        </td>
                                    </tr>

                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f9fafb; padding: 30px 40px; text-align: center; border-top: 1px solid #e5e7eb;">
                                            <p style="margin: 0 0 8px 0; font-size: 14px; color: #6b7280;">
                                                <strong>TelcoNova</strong> - Sistema de Gestión de Órdenes de Trabajo
                                            </p>
                                            <p style="margin: 0; font-size: 12px; color: #9ca3af;">
                                                Este es un email automático, por favor no responder.
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                                """
                .formatted(
                        technician.getNameTecnico(),
                        order.getId(),
                        order.getNombreCliente() != null ? order.getNombreCliente() : "No especificado",
                        order.getDireccion() != null ? order.getDireccion() : "No especificada",
                        order.getZona(),
                        order.getServicio(),
                        priorityColor,
                        priorityText,
                        formattedDate,
                        order.getDescripcion() != null ? order.getDescripcion() : "Sin descripción");
    }
}
