-- Seed users
INSERT INTO usuarios (email_usuario, password_usuario, name_usuario, role_usuario) VALUES (
  'test@example.com',
  '$2a$10$76jDh/40RDpBWeB/YFwSW.V/RPSBnS8AB3A7R271dWHeZyWvKOSri',
  'Test User',
  'Administrator'
);

-- Seed technicians
INSERT INTO tecnicos (name_tecnico, zone_tecnico, workload_tecnico, speciality_tecnico, email_tecnico) VALUES 
  ('Juan Perez', 'Norte', '3', 'Electricidad', 'telconovas@gmail.com'),
  ('Maria Gomez', 'Sur', '2', 'Plomería', 'maria.gomez@telconova.com'),
  ('Carlos Rodriguez', 'Norte', '1', 'Electricidad', 'carlos.rodriguez@telconova.com'),
  ('Ana Martinez', 'Centro', '2', 'HVAC', 'ana.martinez@telconova.com'),
  ('Luis Fernandez', 'Este', '0', 'Redes', 'luis.fernandez@telconova.com'),
  ('Sofia Torres', 'Oeste', '1', 'Plomería', 'sofia.torres@telconova.com');

-- Seed orders with new fields
INSERT INTO ordenes (id, zona, creado_en, servicio, descripcion, nombre_cliente, direccion, prioridad, assigned_to, asignado_en, asignado_por, status) VALUES 
  ('O-1001', 'Norte', '2025-11-20T10:12:00', 'Electricidad', 'Cambio de tablero principal en edificio residencial', 'Edificio Los Pinos', 'Av. Principal 123', 'alta', '1', '2025-11-20T11:00:00', 'test@example.com', 'assigned'),
  ('O-1002', 'Sur', '2025-11-21T11:00:00', 'Plomería', 'Fuga en planta baja, requiere atención urgente', 'Condominio Vista Hermosa', 'Calle 45 #67', 'alta', '2', '2025-11-21T11:30:00', 'test@example.com', 'assigned'),
  ('O-1003', 'Centro', '2025-11-22T09:30:00', 'HVAC', 'Revisión trimestral sistema aire acondicionado', 'Hotel Plaza', 'Centro Comercial 890', 'media', '4', '2025-11-22T10:00:00', 'test@example.com', 'assigned'),
  ('O-1004', 'Oeste', '2025-11-22T14:15:00', 'Redes', 'Cableado estructurado para oficinas', 'Oficinas TechCorp', 'Parque Industrial 234', 'media', NULL, NULL, NULL, 'pending'),
  ('O-1005', 'Este', '2025-11-23T08:45:00', 'Electricidad', 'Cortocircuito en panel de distribución', 'Fábrica El Progreso', 'Zona Industrial 567', 'alta', NULL, NULL, NULL, 'pending'),
  ('O-1006', 'Norte', '2025-11-23T16:20:00', 'Plomería', 'Nueva línea de agua para expansión', 'Residencial Las Flores', 'Urbanización Norte 890', 'baja', NULL, NULL, NULL, 'pending'),
  ('O-1007', 'Sur', '2025-11-24T13:10:00', 'Electricidad', 'Instalación de paneles solares', 'Casa Ecológica', 'Barrio Verde 123', 'media', NULL, NULL, NULL, 'pending'),
  ('O-1008', 'Centro', '2025-11-24T12:30:00', 'Redes', 'Problemas de conectividad en switches', 'Banco Central', 'Av. Financiera 456', 'alta', NULL, NULL, NULL, 'pending');
