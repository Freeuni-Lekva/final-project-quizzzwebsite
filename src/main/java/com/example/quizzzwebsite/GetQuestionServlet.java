package com.example.quizzzwebsite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetQuestionServlet", value = "/GetQuestionServlet")
public class GetQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("gq");
        List<question> questionList = (List<question>) req.getSession().getAttribute("questions");
        Integer currQuestion = (Integer) req.getSession().getAttribute("currQuestion");
        if (req.getParameter("more") != null) {
            if (currQuestion < questionList.size()) {
                resp.getWriter().print("1");
            } else {
                resp.getWriter().print("0");
            }
            return;
        }
        if (req.getParameter("type") != null) {
            resp.getWriter().print(questionList.get(currQuestion).getType());
            return;
        }
        if (req.getParameter("summary") != null) {
            record rec = (record) req.getSession().getAttribute("record");
            long diff = rec.getEndTime().getTime() - rec.getStartTime().getTime();
            PrintWriter pw = resp.getWriter();
            pw.print("<h2>your score is " + rec.getScore() + "</h2>");
            pw.print("<h2>" + diff + " seconds used</h2>");
            return;
        }
        if (currQuestion >= questionList.size()){
            resp.getWriter().print("-1");
            return;
        }
        String question = questionList.get(currQuestion).getHtmlTag();
        resp.getWriter().print(question);
        req.getSession().setAttribute("currQuestion", currQuestion+1);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<question> questionList = (List<question>)req.getSession().getAttribute("questions");
        Integer currQuestion = (Integer)req.getSession().getAttribute("currQuestion");
        question quest = questionList.get(currQuestion);
        int type = questionList.get(currQuestion).getType();
        String answer = req.getParameter("asnwer");
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
        resp.getWriter().print(mark);
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
