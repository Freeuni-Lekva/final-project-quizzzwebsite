package com.example.quizzzwebsite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SubmitAnswerServlet", value = "/SubmitAnswerServlet")
public class SubmitAnswerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hereee");
        List<question> questionList = (List<question>)req.getSession().getAttribute("questions");
        Integer currQuestion = (Integer)req.getSession().getAttribute("currQuestion");
        question quest = questionList.get(currQuestion-1);
        int type = quest.getType();
        String answer = req.getParameter("answer");
        if(req.getParameter("startsubmitting") != null) {
            System.out.println("here1");
            req.getSession().setAttribute("currQuestion", 1);
            PrintWriter pw = resp.getWriter();
            pw.print("ok");
            pw.close();
            return;
        }
        int mark = 0;
        if(type == StaticVariables.FILL_BLANK_NUM) {
            mark = checkFillBlank(answer);
        } else if(type == StaticVariables.MULTIPLE_CHOICE_NUM) {
            mark = checkMultipleChoice(answer);
        } else if(type == StaticVariables.PICTURE_RESPONSE_NUM) {
            mark = checkPictureResponse(answer);
        } else if(type == StaticVariables.QUESTION_RESPONSE_NUM) {
            mark = checkQuestionResponse(answer);
        }
        record rec = (record)req.getSession().getAttribute("record");
        rec.setScore(rec.getScore() + mark);
        PrintWriter pw = resp.getWriter();
        pw.print(answer);
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private int checkQuestionResponse(String answer) {
        return 0;
    }

    private int checkMultipleChoice(String answer) {
        return 0;
    }

    private int checkPictureResponse(String answer) {
        return 0;
    }

    private int checkFillBlank(String answer) {
        return 0;
    }
}
