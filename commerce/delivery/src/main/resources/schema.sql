CREATE TABLE IF NOT EXISTS address
(
    id      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    country VARCHAR NOT NULL,
    city    VARCHAR NOT NULL,
    street  VARCHAR NOT NULL,
    house   VARCHAR NOT NULL,
    flat    VARCHAR
);

CREATE TABLE IF NOT EXISTS delivery
(
    id              UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id        UUID    NOT NULL,
    from_address_id UUID    NOT NULL,
    to_address_id   UUID    NOT NULL,
    state           VARCHAR NOT NULL,
    delivery_weight DOUBLE PRECISION,
    delivery_volume DOUBLE PRECISION,
    fragile         BOOLEAN,
    CONSTRAINT delivery_from_address_fk FOREIGN KEY (from_address_id) REFERENCES address (id) ON DELETE CASCADE,
    CONSTRAINT delivery_to_address_fk FOREIGN KEY (to_address_id) REFERENCES address (id) ON DELETE CASCADE
);