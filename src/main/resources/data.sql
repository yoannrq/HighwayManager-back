-- Roles
INSERT INTO roles (name) VALUES
                             ('Admin'),
                             ('User'),
                             ('Guest')
ON CONFLICT (name) DO NOTHING;

-- Users
INSERT INTO users (firstname, lastname, role_id, email, password) VALUES
                                                                      ('Thomas', 'Martin', 1, 'thomas.martin@company.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKXL3/C9nqbQZza'),
                                                                      ('Marie', 'Dubois', 2, 'marie.dubois@company.com', '$2a$12$z5YyUgz9Yh6d7sEOzEfFBOK4/hmPexDj5sN9JxR3HKEv.w5RwjyXO'),
                                                                      ('Lucas', 'Bernard', 2, 'lucas.bernard@company.com', '$2a$12$mR.2p/8r1AH9HWUYmPmqV.1KzHV3Fy3R/kg9h6cVvz9kgpR.kIR2m'),
                                                                      ('Sophie', 'Petit', 3, 'sophie.petit@company.com', '$2a$12$23Lz0OZB9nHI0BzFSgZxj.UK2YJ7EiCwwS.h8w3JvLBB1HQGvZoii')
ON CONFLICT (email) DO NOTHING;

--Teams
INSERT INTO teams (name, master_id) VALUES
                                        ('Équipe A', 1),
                                        ('Équipe B', 2),
                                        ('Pôle responsables', 1)
ON CONFLICT (name) DO NOTHING;

-- EventTypes
INSERT INTO event_types (name) VALUES
                                  ('Maintenance'),
                                  ('Intervention'),
                                  ('Reunion')
ON CONFLICT (name) DO NOTHING;

-- Status
INSERT INTO status (name) VALUES
                                   ('En attente'),
                                   ('En cours'),
                                   ('En revue'),
                                   ('Cloturé')
ON CONFLICT (name) DO NOTHING;

-- Events
INSERT INTO events (date, team_id, type_id, status_id) VALUES
                                                           ('2024-10-24', 1, 1, 2),
                                                           ('2024-10-25', 2, 3, 1),
                                                           ('2024-10-26', 3, 2, 3),
                                                           ('2024-10-27', 1, 3, 4)
ON CONFLICT DO NOTHING;

-- Reports
INSERT INTO reports (event_id, author_id, content) VALUES
                                                       (1, 1, 'Maintenance des véhicules effectuée.'),
                                                       (2, 2, 'Réunion déquipe : planification de la prochaine intervention et revue des objectifs.'),
                                                       (3, 3, 'Intervention urgente sur les abords de la portion AZ123.')
ON CONFLICT DO NOTHING;

-- Team_has_users (table de liaison)
INSERT INTO team_has_users (user_id, team_id) VALUES
                                                  (1, 1),
                                                  (1, 3),
                                                  (2, 2),
                                                  (3, 1),
                                                  (3, 2),
                                                  (4, 2)
ON CONFLICT DO NOTHING;