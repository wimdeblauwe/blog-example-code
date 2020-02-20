CREATE TABLE task_user
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task
(
    id      SERIAL       NOT NULL,
    user_id SERIAL       NOT NULL,
    name    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE task
    ADD CONSTRAINT FK_task_to_user FOREIGN KEY (user_id) REFERENCES task_user;
