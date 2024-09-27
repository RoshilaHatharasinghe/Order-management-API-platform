CREATE TABLE orders
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_number    VARCHAR(255) ,
    item_name           VARCHAR(255) NOT NULL,
    quantity            INT NOT NULL,
    shipping_address    VARCHAR(255) NOT NULL,
    status              ENUM('CANCELLED', 'DISPATCHED', 'NEW') DEFAULT 'NEW',
    placement_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id             BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
