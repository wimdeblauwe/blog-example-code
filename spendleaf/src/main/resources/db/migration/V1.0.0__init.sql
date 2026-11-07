CREATE TABLE expense
(
    id          UUID PRIMARY KEY,
    description VARCHAR        NOT NULL,
    amount      NUMERIC(10, 2) NOT NULL,
    date        DATE           NOT NULL,
    category    VARCHAR        NOT NULL,
    project     VARCHAR,
    billable    BOOLEAN        NOT NULL,
    notes       VARCHAR,
    status      VARCHAR        NOT NULL
);
