package com.example.quizzzwebsite;

import java.util.List;

public class multipleChoice implements question{
    private String questionText;
    private List<String> probableAnswers;
    private List<String> correctAnswers;
    private int type;
    private int questionID;

    public multipleChoice(String questionText,List<String> probableAnswers, List<String> correctAnswers, int questionID){

        this.questionText = questionText;
        this.probableAnswers=probableAnswers;
        this.correctAnswers=correctAnswers;
        this.questionID=questionID;


    }
    @Override
    public String getHtmlTag(){
        String checkBoxes= StaticVariables.EMPTY_STRING;
        String hiddentype = "<input type=\"hidden\" class=\"type\" value=\""
                + StaticVariables.MULTIPLE_CHOICE_NUM + "\">";
        for(int i=0; i<probableAnswers.size(); i++){
            String tmAnswer=probableAnswers.get(i);
            String tmName= "checkBox";
            checkBoxes+="<input type=\"checkbox\" class=\""+tmName+"\" value=\""+probableAnswers.get(i)+"\"><label for=\""+tmName+"\">" +" "+probableAnswers.get(i)+"</label><br>";
        }
        String result="<div id=\"container\">"+ hiddentype +"<h2>"+questionID+". "+"\" \""+questionText+"</h2><br>"+checkBoxes+"</div><br>";
        return result;
    }

    @Override
    public List<String> getCorrectAnswer() {
        return correctAnswers;
    }

    @Override
    public void setQuestionID(int id) {
        questionID=id;

    }

    public int getQuestionID(){

        return questionID;
    }

    @Override
    public int getType() {
        return StaticVariables.MULTIPLE_CHOICE_NUM;
    }


}
