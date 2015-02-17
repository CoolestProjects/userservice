--liquibase formatted sql
--changeset nking:3 dbms:MySql

ALTER TABLE user ADD COLUMN accept_terms VARCHAR(255);
ALTER TABLE user ADD COLUMN parent_name VARCHAR(255);
ALTER TABLE user ADD COLUMN parent_email VARCHAR(255);
ALTER TABLE user ADD COLUMN parent_mobile VARCHAR(255);

