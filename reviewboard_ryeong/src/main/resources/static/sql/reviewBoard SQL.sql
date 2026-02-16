create table reviewBoard(
num int auto_increment primary key, 
writer varchar(20),
email varchar(50), 
subject varchar(50), 
password varchar(10),
reg_date datetime default now(), 
readcount int default 0, 
stars int,
content varchar(1000),
upload1 varchar(50), 
upload2 varchar(50)
);