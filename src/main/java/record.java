import java.sql.Timestamp;

public class record {
    private int quizID;
    private int userID;
    private int score;
    private int maxScore;
    private Timestamp  startTime;
    private Timestamp endTime;
    public record(){ }
    public void setQuizID(int quizID){ this.quizID=quizID;}
    public void setUserID(int userID){this.userID=userID;}
    public void setScore(int score){this.score=score;}
    public void setMaxScore(int maxScore){this.maxScore=maxScore;}
    public void setStartTime(Timestamp startTime){this.startTime=startTime;}
    public void setEndTime(Timestamp endTime){this.endTime=endTime;}


    public int getUserID(){return userID;}
    public int getScore(){return score;}
    public int getMaxScore(){return maxScore;}
    public Timestamp getStartTime(){return startTime;}
    public Timestamp getEndTime(){return endTime;}
}
