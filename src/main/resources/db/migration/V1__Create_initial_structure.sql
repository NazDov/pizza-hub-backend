CREATE TABLE product (
    id Int AUTO_INCREMENT NOT NULL,
    name nvarchar(255) NOT NULL,
    description nvarchar(255) NOT NULL,
    price decimal(10,2) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE shipping (
    id Int AUTO_INCREMENT NOT NULL,
    first_name nvarchar(255) NOT NULL ,
    last_name nvarchar(255) NOT NULL,
    email nvarchar(255) NOT NULL,
    phone nvarchar(255) NOT NULL,
    city nvarchar(255) NOT NULL,
    address nvarchar(255) NOT NULL,
    zip nvarchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE orders (
    id Int AUTO_INCREMENT NOT NULL,
    date TIMESTAMP(6) NOT NULL,
    shipping_id Int NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

ALTER TABLE orders
    ADD CONSTRAINT fk_order_customer FOREIGN KEY (shipping_id)
        REFERENCES shipping(id)
        ON DELETE Cascade
        ON UPDATE Cascade;

CREATE TABLE order_item (
    id Int AUTO_INCREMENT NOT NULL,
    product_id Int NOT NULL,
    quantity Int Not Null,
    order_id Int Not Null,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

ALTER TABLE order_item
    ADD CONSTRAINT fk_order_item_product FOREIGN KEY (product_id)
        REFERENCES product(id)
        ON DELETE Cascade
        ON UPDATE Cascade;
ALTER TABLE order_item
ADD CONSTRAINT fk_order_item_order FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE Cascade
        ON UPDATE Cascade;

