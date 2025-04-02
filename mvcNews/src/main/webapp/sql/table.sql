create table dailynews(
	num number primary key,
	title varchar2(150) not null,
	writer varchar2(30) not null,
	passwd varchar2(12) not null,
	email varchar2(50) not null,
	article clob not null,
	filename varchar2(480),
	reg_date date default sysdate not null
);

create sequence dailynews_seq;