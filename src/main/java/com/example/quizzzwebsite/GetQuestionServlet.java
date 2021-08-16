package com.example.quizzzwebsite;

import user.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
        } else if (req.getParameter("type") != null) {
            System.out.println("here");
            PrintWriter pw = resp.getWriter();
            pw.print(questionList.get(currQuestion-1).getType());
            pw.close();
        } else if (req.getParameter("summary") != null) {
            record rec = (record) req.getSession().getAttribute("record");
            rec.setEndTime(Timestamp.from(Instant.now()));
            try {
                quizDao.insertRecord(rec);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long diff = rec.getEndTime().getTime() - rec.getStartTime().getTime();
            diff = diff/1000;
            PrintWriter pw = resp.getWriter();
            pw.print("<h2>your score is " + rec.getScore() + "</h2>");
            pw.print("<h2>" + diff + " seconds used</h2>");
            pw.close();
        } else if (currQuestion >= questionList.size()){
            PrintWriter pw = resp.getWriter();
            pw.print("-1");
            pw.close();
        } else {
            if(currQuestion == 0) {
                record rec = new record();
                rec.setStartTime(Timestamp.from(Instant.now()));
                rec.setQuizID(((getQuiz)req.getSession().getAttribute("quiz")).getID());
                rec.setUserID(((User)req.getSession().getAttribute(User.ATTRIBUTE_NAME)).getId());
                req.getSession().setAttribute("record", rec);
            }
            String question = questionList.get(currQuestion).getHtmlTag();
            PrintWriter pw = resp.getWriter();
            pw.print(question);
            pw.close();
            req.getSession().setAttribute("currQuestion", currQuestion+1);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
