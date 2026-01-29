create table board(
	num int auto_increment primary key,
    writer varchar(20),
    subject varchar(30),
    writerPw varchar(20),
    reg_date datetime default now(),
    readcount int default 0,
    content varchar(1000)
);