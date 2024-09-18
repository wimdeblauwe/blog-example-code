CREATE TABLE guitar
(
    id    text primary key,
    brand text,
    model text
);

CREATE TABLE guitar_uuid
(
    id    uuid primary key,
    brand text,
    model text
);

CREATE TABLE guitar_uuid_v7
(
    id    uuid primary key,
    brand text,
    model text
);

CREATE TABLE guitar_tsid
(
    id    bigint primary key,
    brand text,
    model text
);
