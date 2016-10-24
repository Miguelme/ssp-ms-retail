
CREATE TABLE store (
    store_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the store',
    name VARCHAR(255) NOT NULL COMMENT 'The name of the store',
    PRIMARY KEY (store_id)
);
CREATE TABLE product (
    product_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the product',
    store_id BIGINT NOT NULL COMMENT 'The id of the store',
    name VARCHAR(255) NOT NULL COMMENT 'The name of the product',
    description VARCHAR(255) NOT NULL COMMENT 'The description of the product',
    sku VARCHAR(10) NOT NULL COMMENT 'The sku of the product',
    price DECIMAL(5, 2) UNSIGNED NOT NULL COMMENT 'The price of the product',
    PRIMARY KEY (product_id),
    FOREIGN KEY (store_id) REFERENCES store (store_id)
);

CREATE TABLE stock (
    product_id BIGINT NOT NULL COMMENT 'The id of the product',
    store_id BIGINT NOT NULL COMMENT 'The id of the store',
    count BIGINT NOT NULL COMMENT 'The count for the product',
    PRIMARY KEY (product_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    FOREIGN KEY (store_id) REFERENCES store (store_id)
);

CREATE TABLE purchase_order (
    order_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the order',
    store_id BIGINT NOT NULL COMMENT 'The id of the store',
    order_date DATETIME NOT NULL COMMENT 'The date of the order',
    order_status INTEGER NOT NULL COMMENT 'The status of the order',
    first_name VARCHAR(255) NOT NULL COMMENT 'The purchaser first name',
    last_name  VARCHAR(255) NOT NULL COMMENT 'The purchaser last name',
    email VARCHAR(255) NOT NULL COMMENT 'The purchaser email',
    phone VARCHAR(10) NOT NULL COMMENT 'The purchaser phone',
    PRIMARY KEY (order_id),
    FOREIGN KEY (store_id) REFERENCES store (store_id)
);

CREATE TABLE order_product (
    order_product_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the product order',
    product_id BIGINT NOT NULL COMMENT 'The id of the product',
    order_id BIGINT NOT NULL COMMENT 'The id of the order',
    count BIGINT NOT NULL COMMENT 'The quantity of the products',
    PRIMARY  KEY (order_product_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    FOREIGN KEY (order_id) REFERENCES purchase_order (order_id)
);
