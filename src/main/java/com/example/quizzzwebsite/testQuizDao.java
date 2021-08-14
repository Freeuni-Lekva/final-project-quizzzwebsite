package com.example.quizzzwebsite;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class testQuizDao extends TestCase {
    private static final int CREATOR_ID=5;
    private static final int USER_ID=1;
    private static final int SCORE=1;
    private static final int MAX_SCORE=2;
    private static final String QUIZ_NAME="name";
    private static final String QUIZ_DESCRIPTION="description";
    private Timestamp timeStmp;
    private Timestamp recordStart;
    private Timestamp recordEnd;
    private int quizID;
    private int quizID2;
    private getQuiz returnedQuiz;
    private getQuiz retQuiz2;
    private getQuiz returnedNullQuiz;
    private List<question> questionLst;
    private List<record> returnedRecordsByUserID;
    private List<record> returnedRecordsByQuizID;
    private List<record> returnedEmptyRecordsByUserID;
    private List<record> returnedEmptyRecordsByQuizID;



    protected void setUp() throws Exception {
        super.setUp();

        List<questionParam> lst=new ArrayList<questionParam>();
        questionParam qP1=new questionParam(3,"","as","bas");
        questionParam qP2=new questionParam(3,"","dadas","nnnnnnn");

        if(qP1.checkValidate())lst.add(qP1);
        if(qP2.checkValidate())lst.add(qP2);
        timeStmp=Timestamp.from(Instant.now());
        quiz aQ=new addQuiz(  QUIZ_NAME,  CREATOR_ID, timeStmp, QUIZ_DESCRIPTION , false, false, false, true);
         quizID=quizDao.addQUIZ(aQ,lst);

        List<questionParam> lst2=new ArrayList<questionParam>();
        questionParam qP3=new questionParam(1,"","a","bas~");
        questionParam qP4=new questionParam(2,"a~b~c","a~b","nnnnnnn");
        questionParam qP5=new questionParam(4,"a~b","b","nnnnnnnk");
        if(qP3.checkValidate())lst2.add(qP3);
        if(qP4.checkValidate())lst2.add(qP4);
        if(qP5.checkValidate())lst2.add(qP5);

        quiz aQ2=new addQuiz(  QUIZ_NAME+"1",  CREATOR_ID, Timestamp.from(Instant.now()), QUIZ_DESCRIPTION+"aaaaaaaaaaa" , false, false, false, false);
        quizID2=quizDao.addQUIZ(aQ2,lst2);

        retQuiz2=(getQuiz)quizDao.getQUIZ(quizID2);

        returnedQuiz=(getQuiz) quizDao.getQUIZ(quizID);
        questionLst=returnedQuiz.getQuestions();

        quizDao.remove(quizID);
        returnedNullQuiz=(getQuiz) quizDao.getQUIZ(quizID);

        record rec=new record();
        recordStart=Timestamp.from(Instant.now());
        rec.setStartTime(recordStart);
        rec.setQuizID(quizID);
        rec.setScore(SCORE);
        rec.setMaxScore(MAX_SCORE);
        rec.setUserID(USER_ID);
        recordEnd=Timestamp.from(Instant.now());
        rec.setEndTime(recordEnd);

        quizDao.insertRecord(rec);
        returnedRecordsByUserID=quizDao.getRecordsByUserID(USER_ID);
        returnedRecordsByQuizID=quizDao.getRecordsByQuizID(quizID);

        quizDao.removeRecordsByUserID(USER_ID);
        quizDao.removeRecordsByQuizID(quizID);

        returnedEmptyRecordsByQuizID=quizDao.getRecordsByQuizID(quizID);
        returnedEmptyRecordsByUserID=quizDao.getRecordsByUserID(USER_ID);



    }

    @Test
    public void testQuizDataAccessFunctions(){
        assertEquals(quizID,returnedQuiz.getID());
        assertEquals( QUIZ_NAME,returnedQuiz.getName());
        assertEquals(timeStmp,returnedQuiz.getCreationTime());
        assertEquals(QUIZ_DESCRIPTION,returnedQuiz.getDescription());
        assertEquals(false,returnedQuiz.isPages());
        assertEquals(false,returnedQuiz.isPractice());
        assertEquals(false,returnedQuiz.isCorrect());
        assertEquals(true,returnedQuiz.isRandom());

        assertEquals(2,questionLst.size());

        assertEquals(1,questionLst.get(0).getCorrectAnswer().size());
        assertEquals(1,questionLst.get(1).getCorrectAnswer().size());

        assertEquals(null,returnedNullQuiz);

    }

    @Test
    public void testHistoryDataAccessFunctions(){
        assertEquals(quizID,returnedRecordsByQuizID.get(0).getQuizID());
        assertEquals(quizID,returnedRecordsByUserID.get(0).getQuizID());
        assertEquals(USER_ID,returnedRecordsByQuizID.get(0).getUserID());
        assertEquals(USER_ID,returnedRecordsByUserID.get(0).getUserID());

        assertEquals(recordStart,returnedRecordsByQuizID.get(0).getStartTime());
        assertEquals(recordStart,returnedRecordsByUserID.get(0).getStartTime());

        assertEquals(recordEnd,returnedRecordsByQuizID.get(0).getEndTime());
        assertEquals(recordEnd,returnedRecordsByUserID.get(0).getEndTime());

        assertEquals(SCORE,returnedRecordsByQuizID.get(0).getScore());
        assertEquals(SCORE,returnedRecordsByUserID.get(0).getScore());

        assertEquals(MAX_SCORE,returnedRecordsByQuizID.get(0).getMaxScore());
        assertEquals(MAX_SCORE,returnedRecordsByUserID.get(0).getMaxScore());

        assertEquals(0,returnedEmptyRecordsByQuizID.size());
        assertEquals(0,returnedEmptyRecordsByUserID.size());

    }

    @Test
    public void testQuestions(){
        List<question> qList=retQuiz2.getQuestions();
        fillBlank fB=(fillBlank) qList.get(0);
        multipleChoice mC=(multipleChoice) qList.get(1);
        questionResponse qR=(questionResponse) qList.get(2);

        assertEquals("a",fB.getCorrectAnswer().get(0));
        assertEquals(0,fB.getQuestionID());
        assertTrue(!fB.getHtmlTag().equals(""));

        assertEquals("a",mC.getCorrectAnswer().get(0));
        assertEquals("b",mC.getCorrectAnswer().get(1));
        assertEquals(1,mC.getQuestionID());
        assertTrue(!mC.getHtmlTag().equals(""));

        assertEquals("b",qR.getCorrectAnswer().get(0));
        assertEquals(2,qR.getQuestionID());
        assertTrue(!qR.getHtmlTag().equals(""));

        assertTrue(!returnedQuiz.getQuestions().get(0).getHtmlTag().equals(""));

    }
}