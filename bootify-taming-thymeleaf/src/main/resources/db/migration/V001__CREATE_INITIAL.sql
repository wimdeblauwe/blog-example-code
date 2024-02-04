CREATE SEQUENCE  IF NOT EXISTS primary_sequence START WITH 10000 INCREMENT BY 1;

CREATE TABLE "user" (
    id BIGINT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    birthday date,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE team (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    coach_id BIGINT NOT NULL,
    CONSTRAINT team_pkey PRIMARY KEY (id)
);

CREATE TABLE team_player (
    id BIGINT NOT NULL,
    position VARCHAR(100) NOT NULL,
    player_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    CONSTRAINT team_player_pkey PRIMARY KEY (id)
);

ALTER TABLE "user" ADD CONSTRAINT unique_user_email UNIQUE (email);

ALTER TABLE team ADD CONSTRAINT fk_team_coach_id FOREIGN KEY (coach_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE team_player ADD CONSTRAINT fk_team_player_player_id FOREIGN KEY (player_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE team_player ADD CONSTRAINT fk_team_player_team_id FOREIGN KEY (team_id) REFERENCES team (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE team_player ADD CONSTRAINT unique_team_player_player_id UNIQUE (player_id);

