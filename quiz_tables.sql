use testDataBase;



DROP TABLE IF EXISTS quiz.quiz;
DROP TABLE IF EXISTS quiz.questions.question;
DROP TABLE IF EXISTS historyTable;

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