USE testDataBase;
DROP TABLE IF EXISTS t_chats;
-- remove table if it already exists and start from scratch

CREATE TABLE t_chats (
                         sender CHAR(23) ,
                         receiver CHAR(23),
                         message CHAR(250) not null,
                         time     datetime ,
                         unseen bool

);