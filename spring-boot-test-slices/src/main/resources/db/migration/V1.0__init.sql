CREATE SEQUENCE hibernate_sequence;

CREATE TABLE album
(
    id     bigint not null,
    name   text   not null,
    artist text   not null,
    primary key (id)
);
