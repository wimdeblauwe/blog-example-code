CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE book
(
    id    BIGINT NOT NULL,
    title VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE song
(
    id    BIGINT NOT NULL,
    title VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE "user"
(
    id               BIGINT NOT NULL,
    favorite_book_id BIGINT,
    favorite_song_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE "user"
    ADD CONSTRAINT FK_user_to_book FOREIGN KEY (favorite_book_id) REFERENCES book;
ALTER TABLE "user"
    ADD CONSTRAINT FK_user_to_song FOREIGN KEY (favorite_song_id) REFERENCES song;