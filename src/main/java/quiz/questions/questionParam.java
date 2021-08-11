package quiz.questions;

import quiz.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class questionParam {
    private int type;
    private String probableAnswers;
    private String correctAnswers;
    private String txt;

    public questionParam(int type,String probableAnswers, String correctAnswers, String txt){
        this.txt=txt;
        this.type=type;
        this.correctAnswers=correctAnswers;
        this.probableAnswers=probableAnswers;

    }

    public int getType(){
        return type;
    }
    public String getProbableAnswers(){
        return probableAnswers;
    }

    public String getCorrectAnswers(){
        return correctAnswers;
    }

    public String getTxt(){
        return txt;
    }

    public boolean checkValidate(){
        boolean result;
        if(txt.equals("") || correctAnswers.equals(""))return false;
        if((type==2 || type==4) && probableAnswers.equals("")) return false;
        switch (type){
                case 1:
                    result=checkFullIn();
                   break;
                case 3:
                    result=checkPictureResponse();
                    break;
                default:
                    result=checkClosedQuestions() ;
                    break;
        }
        return result;
    }



    private boolean checkPictureResponse() {
        return true;
    }

    private boolean checkClosedQuestions() {
        if(type==4){
            for(int i=0; i<correctAnswers.length(); i++){
                if(correctAnswers.charAt(i)=='~')return false;
            }
        }
        List<String> lst=new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(probableAnswers,"~");

        while (st.hasMoreTokens()) {
            String str=st.nextToken();
            if(lst.contains(str)) return false;
            lst.add(str);
        }
        if(lst.size()<=1)return false;
        st=new StringTokenizer(correctAnswers,"~");
        while (st.hasMoreTokens()) {
            if(!lst.contains(st.nextToken())) return false;
        }
        return true;
    }

    private boolean checkFullIn() {
        int counter1=0;
        for(int i=0; i<txt.length();i++){
            if(txt.charAt(i)=='~'){
                counter1++;
            }
        }
        if(counter1==0)return false;
        int counter2=0;
        StringTokenizer st1 = new StringTokenizer(correctAnswers,"~");
        while (st1.hasMoreTokens()) {
            counter2++;
            st1.nextToken();

        }
        return counter1==counter2;
    }
}
