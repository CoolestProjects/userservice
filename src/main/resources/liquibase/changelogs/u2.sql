--liquibase formatted sql
--changeset nking:2 dbms:MySql

create table role (
id                        bigint auto_increment not null,
name                      varchar(255),
user_id                   bigint,
constraint pk_role primary key (id)
);
ALTER TABLE `role` ADD INDEX `indx_userid` (`user_id`);
