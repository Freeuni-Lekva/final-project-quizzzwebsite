package quiz;

import java.util.List;

public interface question {
    String getHtmlTag();
    List<String> getCorrectAnswer();
    void setQuestionID(int id);
    int getQuestionID();

}
