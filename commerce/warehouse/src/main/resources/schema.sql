DROP TABLE IF EXISTS warehouse_products;

CREATE TABLE IF NOT EXISTS warehouse_products
(
    product_id UUID PRIMARY KEY,
    fragile    boolean          NOT NULL,
    width      DOUBLE PRECISION NOT NULL,
    height     DOUBLE PRECISION NOT NULL,
    depth      DOUBLE PRECISION NOT NULL,
    weight     DOUBLE PRECISION NOT NULL,
    quantity   BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS booking
(
    booking_id  UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id    UUID NOT NULL,
    delivery_id UUID
);

CREATE TABLE IF NOT EXISTS booking_products
(
    booking_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity   BIGINT,
    CONSTRAINT booking_products_pk PRIMARY KEY (booking_id, product_id),
    CONSTRAINT booking_products_booking_fk FOREIGN KEY (booking_id) REFERENCES booking (booking_id) ON DELETE CASCADE
);