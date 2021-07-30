CREATE TABLE team
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE atlete
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name          VARCHAR(255),
    gold_medals   INTEGER,
    silver_medals INTEGER,
    bronze_medals INTEGER,
    CONSTRAINT pk_atlete PRIMARY KEY (id)
);