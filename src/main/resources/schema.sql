CREATE DATABASE IF NOT EXISTS ecommerce_hibernate;

USE ecommerce_hibernate;

-- =========================================
-- Users
-- =========================================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(255) NOT NULL
);

-- =========================================
-- Categories
-- =========================================

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- =========================================
-- Products
-- =========================================

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    category_id BIGINT NOT NULL,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);

-- =========================================
-- Orders
-- =========================================

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- =========================================
-- Order Details
-- =========================================

CREATE TABLE order_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    CONSTRAINT fk_order_details_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_order_details_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);
