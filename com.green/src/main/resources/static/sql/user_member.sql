-- DB(스키마이름) springBootDB

create database springBootDB;

use springBootDB;
-- ┖> 활성화

-- application.properties 파일의 
-- spring.datasource.url=jdbc:mysql://localhost:3306/>> springBootDB << 여기 적음!!

-- 테이블 이름 : user_member
create table user_mamber(
	no int auto_increment primary key,
    id varchar(20) not null unique, 
    pw varchar(100) not null,
    mail varchar(50) not null,
    phone varchar(50) not null,
    reg_date datetime default now(),  -- user 의 등록일
    mod_date datetime default now()
);