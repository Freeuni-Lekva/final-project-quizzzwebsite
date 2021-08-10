import java.util.List;

public class questionResponse implements  question{

    private String questionText;
    private List<String> probableAnswers;
    private List<String> correctAnswers;
    private int type;
    private int questionID;
    private int getQuestionID;

    public questionResponse(String questionText, List<String> probableAnswers, List<String> correctAnswers,  int questionID){

        this.questionText = questionText;
        this.probableAnswers=probableAnswers;
        this.correctAnswers=correctAnswers;
        this.questionID=questionID;
    }
    @Override
    public String getHtmlTag(){
        String radioButtons="";
        for(int i=0; i<probableAnswers.size(); i++){
            String tmAnswer=probableAnswers.get(i);
            String tmName="4"+"-"+getQuestionID();
            radioButtons+="<input type=\"radio\" name=\""+tmName+"\" value=\""+probableAnswers.get(i)+"\"><label for=\""+tmName+"\">" +" "+probableAnswers.get(i)+"</label><br>";
        }
        String result="<div class=\"container\">"+"<h2>"+questionID+". "+"\" \""+questionText+"</h2><br>"+radioButtons+"</div><br>";
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

}
