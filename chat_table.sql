USE testDataBase;
DROP TABLE IF EXISTS t_chats;
-- remove table if it already exists and start from scratch

CREATE TABLE t_chats (
                         sender CHAR(23) not null,
                         receiver CHAR(23) not null ,
                         message CHAR(250) not null,
                         time     datetime not null,
                         unseen bool not null

);