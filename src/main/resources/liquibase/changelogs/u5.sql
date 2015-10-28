--liquibase formatted sql
--changeset nking:4 dbms:MySql

ALTER TABLE `user` ADD COLUMN active BOOLEAN DEFAULT FALSE;