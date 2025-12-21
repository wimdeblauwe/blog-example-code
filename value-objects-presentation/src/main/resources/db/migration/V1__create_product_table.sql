CREATE TABLE product
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255),
    amount   NUMERIC(19, 2),
    currency VARCHAR(3),
    material_cost_amount NUMERIC(19, 2),
    material_cost_currency VARCHAR(3)
);
