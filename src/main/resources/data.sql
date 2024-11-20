-- Roles
INSERT INTO roles (name) VALUES
                             ('Admin'),
                             ('User'),
                             ('Guest')
ON CONFLICT (name) DO NOTHING;

-- Users
INSERT INTO users (firstname, lastname, role_id, email, password) VALUES
                                                                      ('Thomas', 'Martin', 1, 'thomas.martin@company.com', '$2y$12$s4OL4OvWfxtKN2osX7cn6uu0Xu0sdfE0ktFybCtLSlPkjo.3u.Pim'),
                                                                      ('Marie', 'Dubois', 2, 'marie.dubois@company.com', '$2y$12$dq0lEPyuRBEIgI6HueBz0.fGHv.hv96WupF3u0hfIYmW6GoJBb/aK'),
                                                                      ('Lucas', 'Bernard', 2, 'lucas.bernard@company.com', '$2y$12$smKI2Ycm32ArO4dcZEIMWeSOrKQwoEZ1s7RULY1xL7GDYD0zB5e4K'),
                                                                      ('Sophie', 'Petit', 3, 'sophie.petit@company.com', '$2y$12$wq3BI8FM9BwMkcF1HYkVv.cS6dzPapdfdS5IABFNMD6SJp/gjewda')
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