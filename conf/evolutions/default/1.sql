# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  mobile_number             varchar(255),
  coderdojo_id              bigint,
  twitter                   varchar(255),
  date_of_birth             datetime,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

