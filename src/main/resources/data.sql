DROP TABLE IF EXISTS teams CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS reports CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS status CASCADE;
DROP TABLE IF EXISTS event_types CASCADE;
DROP TABLE IF EXISTS team_has_users CASCADE;

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE event_types (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE status (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       firstname VARCHAR(250) NOT NULL,
                       lastname VARCHAR(250) NOT NULL,
                       role_id INT,
                       email VARCHAR(250) NOT NULL UNIQUE,
                       password VARCHAR(250) NOT NULL,
                       FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE teams (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(250) NOT NULL UNIQUE,
                       master_id INT NOT NULL,
                       FOREIGN KEY (master_id) REFERENCES users(id)
);

CREATE TABLE team_has_users (
                                user_id INT,
                                team_id INT,
                                PRIMARY KEY (user_id, team_id),
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE events (
                        id SERIAL PRIMARY KEY,
                        date DATE NOT NULL,
                        team_id INT,
                        type_id INT,
                        status_id INT,
                        FOREIGN KEY (team_id) REFERENCES teams(id),
                        FOREIGN KEY (type_id) REFERENCES event_types(id),
                        FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE reports (
                         id SERIAL PRIMARY KEY,
                         event_id INT,
                         author_id INT,
                         content TEXT NOT NULL,
                         FOREIGN KEY (event_id) REFERENCES events(id),
                         FOREIGN KEY (author_id) REFERENCES users(id)
);
