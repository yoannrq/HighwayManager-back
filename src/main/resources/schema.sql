INSERT INTO roles (name) VALUES
                             ('Admin'),
                             ('User'),
                             ('Guest')
ON CONFLICT (name) DO NOTHING;

INSERT INTO eventtypes (name) VALUES
                                  ('Maintenance'),
                                  ('Intervention'),
                                  ('Reunion')
ON CONFLICT (name) DO NOTHING;