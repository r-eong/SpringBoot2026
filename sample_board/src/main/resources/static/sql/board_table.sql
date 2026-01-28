create table board_table(
	id int auto_increment unique primary key,  -- 게시글 번호
    title varchar(30) not null,  -- 제목
    content varchar(500) not null,  -- 내용
    writer varchar(10) not null,  -- 작성자
    createdAt datetime default now()  -- 작성일
);