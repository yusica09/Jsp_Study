create table todo(
	id number primary key,
	todo varchar2(150) not null,
	created date default sysdate not null,
	completed number(1) default 0 not null -- 할일 수행여부 0 / 1 (미완료 / 완료)
);

create sequence todo_seq;