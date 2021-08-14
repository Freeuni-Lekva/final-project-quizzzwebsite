package com.example.quizzzwebsite;

import java.util.List;

public class fillBlank implements question{
    private String questionText;
    private List<String> correctAnswers;
    private int questionID;
    public fillBlank(String questionText, List<String> correctAnswers, int questionID){

        this.questionText = questionText;
        this.correctAnswers=correctAnswers;
        this.questionID=questionID;
    }
    @Override
    public String getHtmlTag() {
        String textFields= StaticVariables.EMPTY_STRING;
        for(int i=0; i<correctAnswers.size();i++){
            String tmName= StaticVariables.FILL_BLANK_NUM+ StaticVariables.DELIMITER_IN_QUESTION_FIELD_NAME+questionID+ StaticVariables.DELIMITER_IN_QUESTION_FIELD_NAME;
            tmName+=i;
            textFields+="<h3>input answer:<input type=\"text\" name=\""+tmName+"\"/></h3><br>";
        }
        String result="<div class=\"container\">"+"<h2>"+questionID+". "+"\" \""+questionText+"</h2><br>"+textFields+"</div>";

        return result;
    }

    @Override
    public List<String> getCorrectAnswer() {

        return correctAnswers;
    }

    @Override
    public void setQuestionID(int id) { questionID=id; }

    public int getQuestionID(){

        return questionID;
    }


}
