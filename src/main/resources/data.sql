INSERT INTO roles (name) VALUES
                             ('Admin'),
                             ('User'),
                             ('Guest')
ON CONFLICT (name) DO NOTHING;

INSERT INTO event_types (name) VALUES
                                  ('Maintenance'),
                                  ('Intervention'),
                                  ('Reunion')
ON CONFLICT (name) DO NOTHING;

INSERT INTO status (name) VALUES
                                   ('En attente'),
                                   ('En cours'),
                                   ('En revue'),
                                   ('Cloturé')
    ON CONFLICT (name) DO NOTHING;