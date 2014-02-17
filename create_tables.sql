--CREATE DATABASE ash CHARACTER SET utf8 COLLATE utf8_general_ci;

drop table if exists ash.dancer;
drop table if exists ash.club;
drop table if exists ash.event;
drop table if exists ash.metadata; 

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

create table ash.event(
id int not null AUTO_INCREMENT,
name varchar(255) not null,
event_date date,
location_id int,
e_class_pairs int,
d_class_pairs int,
c_class_pairs int,
b_class_pairs int,
a_class_pairs int,
primary key(id)
); 

create table ash.metadata(
id int not null AUTO_INCREMENT,
ref_id int not null,
meta_type varchar(100) not null,
meta_key varchar(100) not null,
meta_value varchar(255) not null,
primary key(id)
);


