use quiz;

SET SQL_SAFE_UPDATES = 0;

DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS historyTable;
drop table if exists announcements;
drop table if exists users;
drop table if exists userFriendships;
drop table if exists friendshipRequests;
drop table if exists userMessages;
drop table if exists t_chats;
drop table if exists users;

create table announcements (
	title varchar(200) not null,
    id int primary key auto_increment,
    creatorName varchar(200) not null,
    text text not null,
    date date not null
);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 1);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 2);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 3);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 1);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 2);

insert into announcements(title,creatorName,text,date) 
values ("title","name","new text",current_date());

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 1);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 2);

insert into announcements(title,creatorName,text,date) 
values ("title","name","some text",current_date() - 3);

CREATE TABLE quiz(
	quizId INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(400) NOT NULL,
	creatorId INT NOT NULL,
	created VARCHAR(4000) NOT NULL,
	description TEXT,
	pages BOOLEAN,
	practice BOOLEAN,
	correct BOOLEAN,
	random BOOLEAN,
	PRIMARY KEY (quizId)
);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("firstQuiz",10,current_timestamp(),"something",1,1,1,1);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("secondQuiz",10,current_timestamp(),"something",1,1,1,1);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("thirdQuiz",10,current_timestamp(),"something",1,1,1,1);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("fourthQuiz",10,current_timestamp(),"something",1,1,1,1);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("fifthQuiz",10,current_timestamp(),"something",1,1,1,1);

insert into quiz(name,creatorId,created,description,pages,practice,correct,random)
values("sixthQuiz",10,current_timestamp(),"something",1,1,1,1);

CREATE TABLE question(
	quizId INT NOT NULL,
    questionId INT NOT NULL,
	questionText TEXT NOT NULL,
	probableAnswer VARCHAR(400),
	correctAnswer VARCHAR(400) NOT NULL,
	type INT NOT NULL
);

CREATE TABLE historyTable (
	quizId INT NOT NULL,
	userId VARCHAR(255) NOT NULL,
	startTime VARCHAR(4000) NOT NULL,
	endTime VARCHAR(4000) NOT NULL,
	score INT NOT NULL,
	maxScore INT NOT NULL
);

create table if not exists users (
	userId bigint not null auto_increment,
    userName varchar(200) not null,
    password varchar(200) not null,
    email varchar(200),
    isAdmin boolean default false,
    unique key (email),
    unique key (userName),
    primary key (userId)
);

insert into users(userName,password,email,isAdmin)
values("admin",'9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684',"admin@gmail.com",true);

insert into users(userName,password,email,isAdmin)
values("user1",'9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684',"user1@gmail.com",false);

insert into users(userName,password,email,isAdmin)
values("user2",'9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684',"user2@gmail.com",false);


create table friendshipRequests (
	requestorUsername varchar(200) not null,
    requesteeUsername varchar(200) not null,
    requestDate timestamp not null
);

select *
from friendshipRequests;

create table  userMessages (
	senderUsername varchar(200),
    receiverUsername varchar(200),
    message text
);

create table userFriendships (
	username1 varchar(200),
    username2 varchar(200)
);

CREATE TABLE  t_chats (
						 sender CHAR(23) ,
                         receiver CHAR(23),
                         message CHAR(250) not null,
                         time     datetime ,
                         unseen bool
);