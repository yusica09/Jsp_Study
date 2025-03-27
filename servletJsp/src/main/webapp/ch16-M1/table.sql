create table smember(
	num number not null,
	id varchar2(12) unique not null,
	name varchar2(30) not null,
	passwd varchar2(12) not null,
	email varchar2(50) not null,
	phone varchar2(15),
	reg_date date default sysdate not null,
	constraint smember_pk primary key(num)
);

create sequence smember_seq;

--게시판
create table sboard(
	board_num number not null,
	title varchar2(250) not null,
	content clob not null,
	ip varchar2(30) not null,
	reg_date date default sysdate not null,
	num number not null,
	constraint sboard_pk primary key(board_num),
	constraint sboard_fk foreign key(num) references smember(num)
);

create sequence sboard_seq;