-- Seed users
INSERT INTO usuarios (email_usuario, password_usuario, name_usuario, role_usuario) VALUES (
  'test@example.com',
  '$2a$10$76jDh/40RDpBWeB/YFwSW.V/RPSBnS8AB3A7R271dWHeZyWvKOSri',
  'Test User',
  'Administrator'
);
-- Seed technicians
INSERT INTO tecnicos (name_tecnico, zone_tecnico, workload_tecnico, speciality_tecnico) VALUES (
  'Juan Perez',
  'Zona Oriente',
  '2',
  'Electricidad'
);

INSERT INTO tecnicos (name_tecnico, zone_tecnico, workload_tecnico, speciality_tecnico) VALUES (
  'Maria Gomez',
  'Zona Occidente',
  '1',
  'Plomería'
);




-- Seed orders (use explicit column list to avoid column order issues)
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1001','zona norte','2025-09-22T10:12:00','Instalación eléctrica','Cambio de tablero principal en edificio residencial',NULL,'pending');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1002','zona sur','2025-09-22T11:00:00','Reparación de cañería','Fuga en planta baja, requiere atención urgente','1','assigned');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1003','zona centro','2025-09-22T09:30:00','Mantenimiento HVAC','Revisión trimestral sistema aire acondicionado',NULL,'pending');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1004','zona oeste','2025-09-22T14:15:00','Instalación de red','Cableado estructurado para oficinas','1','assigned');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1005','zona este','2025-09-22T08:45:00','Reparación eléctrica','Cortocircuito en panel de distribución',NULL,'pending');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1006','zona norte','2025-09-22T16:20:00','Instalación de plomería','Nueva línea de agua para expansión','2','assigned');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1007','zona sur','2025-09-22T13:10:00','Mantenimiento preventivo','Revisión general de instalaciones',NULL,'pending');
MERGE INTO ordenes (id, zona, creado_en, servicio, descripcion, assigned_to, status) KEY(id) VALUES ('O-1008','zona centro','2025-09-22T12:30:00','Reparación de red','Problemas de conectividad en switches',NULL,'pending');

