DROP TABLE IF EXISTS teams CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS reports CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS eventtypes CASCADE;
DROP TABLE IF EXISTS team_has_users CASCADE;

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE eventtypes (
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
                        team_id INT,
                        date DATE NOT NULL,
                        type VARCHAR(250) NOT NULL,
                        statut VARCHAR(250),
                        FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE reports (
                         id SERIAL PRIMARY KEY,
                         event_id INT,
                         author_id INT,
                         content TEXT NOT NULL,
                         FOREIGN KEY (event_id) REFERENCES events(id),
                         FOREIGN KEY (author_id) REFERENCES users(id)
);
