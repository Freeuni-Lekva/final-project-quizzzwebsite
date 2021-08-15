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
        String hiddentype = "<input type=\"hidden\" class=\"type\" value=\""
                + StaticVariables.FILL_BLANK_NUM + "\">";
        for(int i=0; i<correctAnswers.size();i++){
            String tmName= "blank";
            textFields+="<h3>input answer:<input type=\"text\" class=\""+tmName+"\"/></h3><br>";
        }
        String result="<div class=\"container\">"+ hiddentype +"<h2>"+questionID+". "+"\" \""+questionText+"</h2><br>"+textFields+"</div>";

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

    @Override
    public int getType() {
        return StaticVariables.FILL_BLANK_NUM;
    }


}
