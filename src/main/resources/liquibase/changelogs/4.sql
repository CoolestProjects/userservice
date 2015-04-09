--liquibase formatted sql
--changeset nking:4 dbms:MySql

ALTER TABLE `user` ADD COLUMN mailingList varchar(255);