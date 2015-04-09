--liquibase formatted sql
--changeset nking:4 dbms:MySql

ALTER TABLE `user` ADD COLUMN mailing_list varchar(255);