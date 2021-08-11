package quiz;

import quiz.questions.question;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class getQuiz implements quiz{
    private int ID;
    private String name;
    private String description;
    private int creatorID;
    private Timestamp created;
    private boolean practice;
    private boolean correct;
    private boolean pages;
    private List<question> questionList=new ArrayList<question>();
    private boolean random;
    public getQuiz(int ID,String name,  int creatorID, Timestamp created, String description, boolean pages, boolean practice, boolean correct, boolean random) throws SQLException, ClassNotFoundException{
        this.ID=ID;
        this.name=name;
        this.description=description;
        this.creatorID=creatorID;
        this.created=created;
        this.pages=pages;
        this.correct=correct;
        this.practice=practice;
        this.random=random;
        questionList= quizDao.getQuestionList(getID());
        if(random==true){
           questionList= quizDao.getRandomisedQuestionsList(questionList);
        }
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public int getCreatorID(){
        return creatorID;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreationTime() {
        return created;
    }
    public boolean isPractice(){
        return practice;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isPages() {
        return pages;
    }
    public List<question> getQuestions(){
        return questionList;
    }

    @Override
    public boolean isRandom() {
        return random;
    }
}
