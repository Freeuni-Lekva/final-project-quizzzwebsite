package quiz;

import java.sql.Timestamp;

public class addQuiz implements quiz {

    private String name;
    private String description;
    private int creatorID;
    private Timestamp created;
    private boolean practice;
    private boolean correct;
    private boolean pages;
    private boolean random;
    public addQuiz(String name,  int creatorID, Timestamp created, String description, boolean pages, boolean practice, boolean correct, boolean random){
        this.name=name;
        this.description=description;
        this.creatorID=creatorID;
        this.created=created;
        this.pages=pages;
        this.correct=correct;
        this.practice=practice;
        this.random=random;
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

    public boolean isRandom(){
        return random;
    }
}
