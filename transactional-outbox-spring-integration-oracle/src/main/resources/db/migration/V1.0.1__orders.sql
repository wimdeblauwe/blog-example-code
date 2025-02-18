-- CREATE SEQUENCE order_seq;

CREATE TABLE "order"
(
    id             NUMBER PRIMARY KEY,
    amount         NUMBER,
    customer_email VARCHAR2(255)
);

-- Create a sequence for the id
CREATE SEQUENCE order_seq START WITH 1 INCREMENT BY 1;

-- Create a trigger to auto-increment the id
CREATE OR REPLACE TRIGGER order_id_trigger
    BEFORE INSERT ON "order"
    FOR EACH ROW
BEGIN
    :new.id := order_seq.NEXTVAL;
END;
/