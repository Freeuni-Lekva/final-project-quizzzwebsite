package com.example.quizzzwebsite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import DBConnect.DB;
import DBConnect.DataSrc;

public class quizDao {
    private static int numQuizzes = 0;
    private static boolean numQuizzesChanged = true;
    private static int numAttempts = 0;
    private static boolean numAttemptsChanged = true;
    private static final String DATABASE_NAME="quiz";
    private static final String QUIZ_TABLE_NAME="quiz";
    private static final String QUESTION_TABLE_NAME="question";
    private static final String HISTORY_TABLE_NAME="historyTable";
    private static String useQuery="USE "+DATABASE_NAME+";";
    private static String getQuery="SELECT * FROM "+QUESTION_TABLE_NAME+" WHERE quizId=? ORDER BY questionId ASC;";

    public static List<question> getQuestionList(int ID) throws SQLException, ClassNotFoundException{
        List<question> res=new ArrayList<question>();

            DB db=new DB();
            Connection con=db.getConnection();
            con.createStatement().executeQuery(useQuery);
            PreparedStatement prepStat= con.prepareStatement(getQuery);
            prepStat.setString(1, String.valueOf(ID));
            ResultSet rs=prepStat.executeQuery();

            while(rs.next()){
                int questionID=rs.getInt(2);
                String questionTxt=rs.getString(3);
                List<String> lstProbableAnswers=getList(rs.getString(4));
                List<String> lstCorrectAnswers=getList(rs.getString(5));
                int type=rs.getInt(6);
               question tmQuestion;

               switch (type){
                   case StaticVariables.FILL_BLANK_NUM:
                       tmQuestion=new fillBlank(questionTxt,lstCorrectAnswers,questionID);
                       break;
                   case StaticVariables.MULTIPLE_CHOICE_NUM:
                       tmQuestion=new multipleChoice(questionTxt,lstProbableAnswers,lstCorrectAnswers,questionID);
                       break;
                   case StaticVariables.PICTURE_RESPONSE_NUM:
                       tmQuestion=new pictureResponse(questionTxt,lstCorrectAnswers,questionID);
                       break;
                   default:
                       tmQuestion=new questionResponse(questionTxt,lstProbableAnswers,lstCorrectAnswers,questionID);
                       break;
               }
               res.add(tmQuestion);
            }

        return res;
    }

    public static int addQUIZ(quiz addableQuiz, List<questionParam> lst) throws SQLException, ClassNotFoundException{
        String str=""+addableQuiz.getCreationTime();
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat= con.prepareStatement("INSERT INTO "+QUIZ_TABLE_NAME+" (name, creatorId, created,description, pages, practice, correct, random) VALUES(?,?,?,?,?,?,?,?);");
        prepStat.setString(1,addableQuiz.getName());
        prepStat.setInt(2,addableQuiz.getCreatorID());
        prepStat.setString(3,str);
        prepStat.setString(4, addableQuiz.getDescription());
        prepStat.setBoolean(5, addableQuiz.isPages());
        prepStat.setBoolean(6,addableQuiz.isPractice());
        prepStat.setBoolean(7,addableQuiz.isCorrect());
        prepStat.setBoolean(8,addableQuiz.isRandom());
        prepStat.executeUpdate();
        ResultSet rs=con.createStatement().executeQuery("SELECT * FROM "+QUIZ_TABLE_NAME+" WHERE created = '"+str+"';");

        int ID=0;
       while(rs.next()) {
            ID = rs.getInt(1);
       }

        for(int i=0; i<lst.size(); i++){
            addQuestion(lst.get(i),ID,i);
        }
        numQuizzes++;
        numQuizzesChanged = true;
        return ID;
     }

    private static void addQuestion(questionParam questionParam, int ID, int questionID) throws SQLException, ClassNotFoundException {

        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat= con.prepareStatement("INSERT INTO "+QUESTION_TABLE_NAME+" (quizId, questionId, questionText,probableAnswer,correctAnswer,type ) VALUES(?,?,?,?,?,?);");
        prepStat.setInt(1,ID);
        prepStat.setInt(2,questionID);
        prepStat.setString(3,questionParam.getTxt());
        prepStat.setString(4,questionParam.getProbableAnswers());
        prepStat.setString(5,questionParam.getCorrectAnswers());
        prepStat.setInt(6,questionParam.getType());
        prepStat.executeUpdate();
    }

    public static List<getQuiz> getQuizByName(String quizName) throws SQLException, ClassNotFoundException {
        List<getQuiz> list = new ArrayList<getQuiz>();
        Connection con = DataSrc.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement statement = con.prepareStatement("select * from " + QUIZ_TABLE_NAME + " where name like ?;");
        statement.setString(1,"%" + quizName + "%");
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            list.add(new getQuiz(rs.getInt(1),rs.getString(2),rs.getInt(3),
                    Timestamp.valueOf(rs.getString(4)),rs.getString(5),
                    rs.getBoolean(6),rs.getBoolean(7),rs.getBoolean(8),
                    rs.getBoolean(9)));
        }
        return list;
    }

    public static List<getQuiz> getUserQuizzes(int userId) throws SQLException, ClassNotFoundException {
        List<getQuiz> list = new ArrayList<getQuiz>();
        Connection con = DataSrc.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement statement = con.prepareStatement("select * from " + QUIZ_TABLE_NAME + " where creatorId = ?;");
        statement.setInt(1,userId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            list.add(new getQuiz(rs.getInt(1),rs.getString(2),rs.getInt(3),
                    Timestamp.valueOf(rs.getString(4)),rs.getString(5),
                    rs.getBoolean(6),rs.getBoolean(7),rs.getBoolean(8),
                    rs.getBoolean(9)));
        }
        return list;
    }

    public static quiz getQUIZ(int ID) throws SQLException, ClassNotFoundException {
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat=con.prepareStatement("SELECT * FROM "+QUIZ_TABLE_NAME+" WHERE quizId=?;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        prepStat.setInt(1,ID);

        ResultSet rs=prepStat.executeQuery();

        if(!rs.first())return null;

         quiz resultQuiz = new getQuiz(ID,rs.getString(2),rs.getInt(3),
                 Timestamp.valueOf(rs.getString(4)),rs.getString(5),
                 rs.getBoolean(6),rs.getBoolean(7),rs.getBoolean(8),
                 rs.getBoolean(9));
        return resultQuiz;
    }

    public static List<getQuiz> getAllQuizzes() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<getQuiz> list = new ArrayList<getQuiz>();
        Connection con = DataSrc.getConnection();
        statement = con.prepareStatement("select * from " + QUIZ_TABLE_NAME + ";");
        rs = statement.executeQuery();
        while(rs.next()){
            list.add(new getQuiz(rs.getInt("quizId"),rs.getString("name"),rs.getInt("creatorId"),
                    Timestamp.valueOf(rs.getString("created")),rs.getString("description"),
                    rs.getBoolean("pages"),rs.getBoolean("practice"),
                    rs.getBoolean("correct"),rs.getBoolean("random")));
        }
        if(statement != null)statement.close();
        if(rs != null)rs.close();
        if(con != null)con.close();
        return list;
    }

    public static List<getQuiz> getQuizzes(int number) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<getQuiz> list = new ArrayList<getQuiz>();
        Connection con = DataSrc.getConnection();
        statement = con.prepareStatement("select * from " + QUIZ_TABLE_NAME + " limit ?;");
        statement.setInt(1,number);
        rs = statement.executeQuery();
        while(rs.next()){
            list.add(new getQuiz(rs.getInt("quizId"),rs.getString("name"),rs.getInt("creatorId"),
                    Timestamp.valueOf(rs.getString("created")),rs.getString("description"),
                    rs.getBoolean("pages"),rs.getBoolean("practice"),
                    rs.getBoolean("correct"),rs.getBoolean("random")));
        }
        if(statement != null)statement.close();
        if(rs != null)rs.close();
        if(con != null)con.close();
        return list;
    }

    private static List<String> getList (String str) {
        List<String > res=new ArrayList<String>();
        if(str.equals(null))return res;
        StringTokenizer st = new StringTokenizer(str,"~");
        while (st.hasMoreTokens()) {
            res.add(st.nextToken());
        }
        return res;
    }

    public static List<question> getRandomisedQuestionsList(List<question> lst){
        Random rand=new Random();
        List<question> result=new ArrayList<question>();
        int size=lst.size();
        for(int i=0; i<size;i++){
            int random= rand.nextInt(lst.size());
            lst.get(random).setQuestionID(i);
            result.add(lst.get(random));
            lst.remove(random);
        }
        return result;
    }

    public static void  remove(int ID) throws SQLException, ClassNotFoundException{
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat1=con.prepareStatement("DELETE FROM "+QUIZ_TABLE_NAME+" WHERE quizId=?;");
        prepStat1.setInt(1,ID);
        prepStat1.executeUpdate();
        PreparedStatement prepStat2=con.prepareStatement("DELETE FROM "+QUESTION_TABLE_NAME+" WHERE quizId=?;");
        prepStat2.setInt(1,ID);
        prepStat2.executeUpdate();
        numQuizzes--;
        removeRecordsByQuizID(ID);

    }

    public static void insertRecord(record record) throws SQLException, ClassNotFoundException{
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        String startTm=""+record.getStartTime();
        String endTm=""+record.getEndTime();
        PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO "+HISTORY_TABLE_NAME+" (quizId,userId,startTime,endTime,score,maxScore) VALUES(?,?,?,?,?,?);");
        preparedStatement.setInt(1,record.getQuizID());
        preparedStatement.setInt(2,record.getUserID());
        preparedStatement.setString(3,startTm);
        preparedStatement.setString(4,endTm);
        preparedStatement.setInt(5,record.getScore());
        preparedStatement.setInt(6,record.getMaxScore());
        preparedStatement.executeUpdate();
        numAttempts++;
    }

    // removes all records about particular quiz
    public static void removeRecordsByQuizID(int quizID) throws SQLException, ClassNotFoundException{
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat1=con.prepareStatement("DELETE FROM "+HISTORY_TABLE_NAME+" WHERE quizId=?;");
        prepStat1.setInt(1,quizID);
        prepStat1.executeUpdate();
    }

    //removes all records about particular user
    public static void removeRecordsByUserID(int userID) throws SQLException, ClassNotFoundException{
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat1=con.prepareStatement("DELETE FROM "+HISTORY_TABLE_NAME+" WHERE userId=?;");
        prepStat1.setInt(1,userID);
        prepStat1.executeUpdate();
    }

    public static List<record>  getRecordsByQuizID(int ID) throws SQLException, ClassNotFoundException{
        List<record> result=new ArrayList<record>();
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat=con.prepareStatement("SELECT * FROM "+HISTORY_TABLE_NAME+" WHERE quizId=?;");
        prepStat.setInt(1,ID);
        ResultSet rs = prepStat.executeQuery();

        while(rs.next()){
            record rec=new record();
            rec.setQuizID(rs.getInt(1));
            rec.setUserID(rs.getInt(2));
            rec.setStartTime(Timestamp.valueOf(rs.getString(3)));
            rec.setEndTime(Timestamp.valueOf(rs.getString(4)));
            rec.setScore(rs.getInt(5));
            rec.setMaxScore(rs.getInt(6));
            result.add(rec);
        }
        return result;
    }

    public static List<record>  getRecordsByUserID(int ID) throws SQLException, ClassNotFoundException{
        List<record> result=new ArrayList<record>();
        DB db=new DB();
        Connection con=db.getConnection();
        con.createStatement().executeQuery(useQuery);
        PreparedStatement prepStat=con.prepareStatement("SELECT * FROM "+HISTORY_TABLE_NAME+" WHERE userId=?;");
        prepStat.setInt(1,ID);
        ResultSet rs = prepStat.executeQuery();

        while(rs.next()){
            record rec=new record();
            rec.setQuizID(rs.getInt(1));
            rec.setUserID(rs.getInt(2));
            rec.setStartTime(Timestamp.valueOf(rs.getString(3)));
            rec.setEndTime(Timestamp.valueOf(rs.getString(4)));
            rec.setScore(rs.getInt(5));
            rec.setMaxScore(rs.getInt(6));
            result.add(rec);
        }
        return result;
    }

    public static int getUserTotalScore(int userId) throws SQLException, ClassNotFoundException {
        int totalScore = 0;
        List<record> list = getRecordsByUserID(userId);
        for(int i = 0;i < list.size();i++){
            totalScore += list.get(i).getScore();
        }
        return totalScore;
    }
    public static int getNumQuizzes() throws SQLException {
        if(numQuizzesChanged){
            Connection con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("select * from quiz;");
            ResultSet rs = statement.executeQuery();
            if(rs == null){
                return numQuizzes;
            }
            numQuizzes = 0;
            while(rs.next()){
                numQuizzes++;
            }
            numQuizzesChanged = false;
        }
        return numQuizzes;
    }

    public static int getNumAttempts() throws SQLException {
        if(numAttemptsChanged){
            Connection con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("select * from historyTable;");
            ResultSet rs = statement.executeQuery();
            if(rs == null){
                return 0;
            }
            numAttempts= 0;
            while(rs.next()){
                numAttempts++;
            }
            numAttemptsChanged = false;
        }
        return numAttempts;
    }

}
