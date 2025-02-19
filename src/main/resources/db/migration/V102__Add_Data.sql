-- Добавление данных в таблицу leagues
INSERT INTO leagues (name, created_at, updated_at)
VALUES ('Premier League', NOW(), NOW()),
       ('La Liga', NOW(), NOW()),
       ('Bundesliga', NOW(), NOW()),
       ('Serie A', NOW(), NOW()),
       ('Ligue 1', NOW(), NOW()),
       ('Eredivisie', NOW(), NOW()),
       ('Primeira Liga', NOW(), NOW()),
       ('MLS', NOW(), NOW()),
       ('Russian Premier League', NOW(), NOW()),
       ('J1 League', NOW(), NOW());

-- Добавление данных в таблицу teams
INSERT INTO teams (name, coach_id, league_id, created_at, updated_at)
VALUES ('Team A', NULL, 1, NOW(), NOW()),
       ('Team B', NULL, 2, NOW(), NOW()),
       ('Team C', NULL, 3, NOW(), NOW()),
       ('Team D', NULL, 4, NOW(), NOW()),
       ('Team E', NULL, 5, NOW(), NOW()),
       ('Team F', NULL, 6, NOW(), NOW()),
       ('Team G', NULL, 7, NOW(), NOW()),
       ('Team H', NULL, 8, NOW(), NOW()),
       ('Team I', NULL, 9, NOW(), NOW()),
       ('Team J', NULL, 10, NOW(), NOW());

-- Добавление данных в таблицу coaches
INSERT INTO coaches (name, surname, team_id, created_at, updated_at)
VALUES ('John', 'Doe', 1, NOW(), NOW()),
       ('Mike', 'Smith', 2, NOW(), NOW()),
       ('Alex', 'Brown', 3, NOW(), NOW()),
       ('David', 'White', 4, NOW(), NOW()),
       ('Chris', 'Black', 5, NOW(), NOW()),
       ('Tom', 'Green', 6, NOW(), NOW()),
       ('Sam', 'Adams', 7, NOW(), NOW()),
       ('Nick', 'Carter', 8, NOW(), NOW()),
       ('Paul', 'Walker', 9, NOW(), NOW()),
       ('Steve', 'Taylor', 10, NOW(), NOW());

-- Обновление coach_id в teams
UPDATE teams
SET coach_id = id;

-- Добавление данных в таблицу players
INSERT INTO players (name, surname, team_id, created_at, updated_at)
VALUES ('Player1', 'One', 1, NOW(), NOW()),
       ('Player2', 'Two', 2, NOW(), NOW()),
       ('Player3', 'Three', 3, NOW(), NOW()),
       ('Player4', 'Four', 4, NOW(), NOW()),
       ('Player5', 'Five', 5, NOW(), NOW()),
       ('Player6', 'Six', 6, NOW(), NOW()),
       ('Player7', 'Seven', 7, NOW(), NOW()),
       ('Player8', 'Eight', 8, NOW(), NOW()),
       ('Player9', 'Nine', 9, NOW(), NOW()),
       ('Player10', 'Ten', 10, NOW(), NOW());

-- Добавление данных в таблицу matches
INSERT INTO matches (league_id, home_team_id, away_team_id, match_datetime, created_at, updated_at)
VALUES (1, 1, 2, NOW(), NOW(), NOW()),
       (2, 3, 4, NOW(), NOW(), NOW()),
       (3, 5, 6, NOW(), NOW(), NOW()),
       (4, 7, 8, NOW(), NOW(), NOW()),
       (5, 9, 10, NOW(), NOW(), NOW()),
       (6, 2, 3, NOW(), NOW(), NOW()),
       (7, 4, 5, NOW(), NOW(), NOW()),
       (8, 6, 7, NOW(), NOW(), NOW()),
       (9, 8, 9, NOW(), NOW(), NOW()),
       (10, 10, 1, NOW(), NOW(), NOW());
