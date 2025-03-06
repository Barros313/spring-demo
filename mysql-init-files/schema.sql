CREATE DATABASE IF NOT EXISTS customers
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE customers;

CREATE TABLE IF NOT EXISTS segment (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    segment_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_segment_id FOREIGN KEY (segment_id) REFERENCES segment(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE INDEX idx_segment_id ON customer(segment_id);

CREATE TABLE document_types (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS customer_documents (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT UNSIGNED NOT NULL,
    document_type_id BIGINT UNSIGNED NOT NULL,
    document VARCHAR(255) NOT NULL,
    CONSTRAINT fk_document_type_id FOREIGN KEY (document_type_id) REFERENCES document_types(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE INDEX idx_document_type_id ON customer_documents(document_type_id);

CREATE TABLE customer_contacts_types (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE customer_contacts (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT UNSIGNED NOT NULL,
    customer_contact_type_id BIGINT UNSIGNED NOT NULL,
    contact_value VARCHAR(255) NOT NULL,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_customer_contact_type_id FOREIGN KEY (customer_contact_type_id) REFERENCES customer_contacts_types(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE INDEX idx_customer_contacts ON customer_contacts(customer_contact_type_id);

INSERT INTO segment(name) VALUES ('Classic');
INSERT INTO segment(name) VALUES ('Poupança');
INSERT INTO segment(name) VALUES ('Next');
INSERT INTO segment(name) VALUES ('Black');
INSERT INTO segment(name) VALUES ('Prime');

INSERT INTO customer(name, segment_id) VALUES ('Maria da Silva', 1);
INSERT INTO customer(name, segment_id) VALUES ('João Penca', 2);
INSERT INTO customer(name, segment_id) VALUES ('Joana Chica', 3);
INSERT INTO customer(name, segment_id) VALUES ('Juliana Giu', 4);
INSERT INTO customer(name, segment_id) VALUES ('Alex Freixo', 4);
INSERT INTO customer(name, segment_id) VALUES ('Lucas Silva', 5);

INSERT INTO document_types(name) VALUES ('CPF');
INSERT INTO document_types(name) VALUES ('RG');

INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (1, 1, '12345678901');
INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (2, 1, '23456789012');
INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (3, 1, '34567890123');
INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (4, 1, '45678901234');
INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (5, 1, '56789012345');
INSERT INTO customer_documents (customer_id, document_type_id, document) VALUES (6, 1, '67890123456');

INSERT INTO customer_contacts_types (name) VALUES ('celular');
INSERT INTO customer_contacts_types (name) VALUES ('e-mail');

INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (1, 1, '1234567890');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (1, 2, 'maria.silva@example.com');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (2, 1, '2345678901');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (2, 2, 'joao.penca@example.com');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (3, 1, '3456789012');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (3, 2, 'joana.chica@example.com');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (4, 1, '4567890123');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (4, 2, 'juliana.giu@example.com');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (5, 1, '5678901234');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (5, 2, 'alex.freixo@example.com');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (6, 1, '6789012345');
INSERT INTO customer_contacts (customer_id, customer_contact_type_id, contact_value) VALUES (6, 2, 'lucas.silva@example.com');