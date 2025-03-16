CREATE TABLE IF NOT EXISTS leagues
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(225) NOT NULL UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS teams
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(225) NOT NULL UNIQUE,
    coach_id BIGINT,
    league_id BIGINT REFERENCES leagues(id) ON DELETE SET NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS coaches
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(225) NOT NULL,
    surname VARCHAR(225) NOT NULL,
    team_id BIGINT REFERENCES teams(id) ON DELETE SET NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS matches
(
    id SERIAL PRIMARY KEY,
    league_id BIGINT REFERENCES leagues(id) ON DELETE CASCADE,
    home_team_id BIGINT REFERENCES teams(id) ON DELETE CASCADE,
    away_team_id BIGINT REFERENCES teams(id) ON DELETE CASCADE,
    match_datetime TIMESTAMP NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS players
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(225) NOT NULL,
    surname VARCHAR(225) NOT NULL,
    goals INT DEFAULT 0,
    assists INT DEFAULT 0,
    team_id BIGINT REFERENCES teams(id) ON DELETE SET NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

ALTER TABLE teams ADD FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE SET NULL;