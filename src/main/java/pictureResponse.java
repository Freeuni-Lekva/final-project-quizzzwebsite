import java.util.List;

public class pictureResponse implements question{
    private String imigeAddress;
    private List<String> correctAnswers;
    private int questionID;
    public pictureResponse(String imigeAddress,   List<String> correctAnswers, int questionID){

        this.imigeAddress = imigeAddress;
        this.correctAnswers=correctAnswers;
        this.questionID=questionID;
    }
    @Override
    public String getHtmlTag(){
        String name="3"+"-"+questionID+"-"+"0";
        String defaultText="photo question";
        String head="<h2>"+questionID+ ". "+defaultText+"</h2><br>";
        String imgSet="<img src=\""+imigeAddress+"\"><br>";
        String textField="<h3>input correct answer</h3><input type=\"text\" name=\""+name+"\"><br>";
        String result="<div class=\"container\">"+head+imgSet+textField+"</div><br>";
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
