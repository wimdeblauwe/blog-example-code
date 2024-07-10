-- CREATE SEQUENCE order_seq;

CREATE TABLE "order"
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    amount         DECIMAL,
    customer_email VARCHAR(255)
);