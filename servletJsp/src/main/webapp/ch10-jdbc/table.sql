create table score(
	id varchar2(10) primary key,
	name varchar2(30) not null,
	korean number(3) not null,
	english number(3) not null,
	math number(3) not null,
	sum number(3) not null,
	avg number(3) not null,
	grade varchar2(2) not null,
	reg_date date not null
);