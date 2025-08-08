DROP TABLE IF EXISTS products;

CREATE TABLE IF NOT EXISTS products
(
    id
                     UUID
        DEFAULT
            gen_random_uuid
            (
            ) PRIMARY KEY,
    product_name     VARCHAR NOT NULL,
    description      VARCHAR NOT NULL,
    image_src        VARCHAR,
    quantity_state   VARCHAR NOT NULL,
    product_state    VARCHAR NOT NULL,
    product_category VARCHAR NOT NULL,
    price            DOUBLE PRECISION
);