--liquibase formatted sql
--changeset nking:1 dbms:MySql

ALTER TABLE user ADD COLUMN coderdojo_uuid varchar(255) DEFAULT NULL;
ALTER TABLE user ADD COLUMN city varchar(255) DEFAULT NULL;
ALTER TABLE user ADD COLUMN country varchar(255) DEFAULT NULL;
ALTER TABLE user ADD COLUMN language varchar(255) DEFAULT NULL;
ALTER TABLE user ADD COLUMN role varchar(255) DEFAULT NULL;

