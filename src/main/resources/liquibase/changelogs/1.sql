--liquibase formatted sql
--changeset nking:1 dbms:MySql

create table user (
id                        bigint auto_increment not null,
email                     varchar(255),
firstname                 varchar(255),
lastname                  varchar(255),
mobile_number             varchar(255),
password                  varchar(100),
coderdojo_id              bigint,
twitter                   varchar(255),
date_of_birth             datetime,
profile_image             varchar(255),
constraint pk_user primary key (id)
);

ALTER TABLE `user` ADD UNIQUE INDEX `indx_username` (`email`);
ALTER TABLE `user` ADD INDEX `indx_coderdojo` (`coderdojo_id`);

