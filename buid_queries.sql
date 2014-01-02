CREATE DATABASE ash CHARACTER SET utf8 COLLATE utf8_general_ci;

drop table if exists dancer;
drop table if exists ash.club; 

create table ash.dancer(
id int not null,
name varchar(255) not null,
lastname varchar(255) not null,
familyname varchar(255),
gender char(1) not null,
currentclass char(1),
clubid int not null,
registration_tms date,
PRIMARY KEY (id)
);

create table ash.club(
id int not null,
name varchar(255) not null,
country varchar(255),
city varchar(255),
website varchar(255),
primary key (id)
); 


