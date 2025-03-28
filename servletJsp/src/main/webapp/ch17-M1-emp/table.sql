-- 사원관리
create table semployee(
	snum number primary key,
	id varchar2(12) unique not null,
	name varchar2(30) not null,
	passwd varchar2(12) not null,
	salary number(8) not null,
	job varchar2(30) not null,
	reg_date date default sysdate not null --입사일
);

create sequence semployee_seq;

-- 사내게시판
create table story(
	story_num number primary key,
	title varchar2(150) not null,
	content clob not null,
	ip varchar2(30) not null,
	snum number not null references semployee(sum),
	reg_date date default sysdate not null
);

create sequence story_seq;