--MVC 게시판
create table svboard(
 num number not null,
 title varchar2(150) not null,
 name varchar2(30) not null,
 passwd varchar2(12) not null,
 email varchar2(50) not null,
 content varchar2(50) not null,
 ip varchar2(30) not null,
 reg_date date default sysdate not null,
 constraint svboard_pk primary key (num)
);

create sequence svboard_seq;