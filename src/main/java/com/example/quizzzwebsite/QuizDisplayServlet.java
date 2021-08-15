package com.example.quizzzwebsite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "QuizDisplayServlet", value = "/QuizDisplayServlet")
public class QuizDisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("qd");
        int id = Integer.parseInt(req.getParameter("id"));
        quiz quizToDisplay = null;
        List<question> questionList = null;
        try {
            quizToDisplay = quizDao.getQUIZ(id);
            questionList = quizDao.getQuestionList(id);
        } catch (Exception e) {
            resp.sendRedirect("Error.jsp?id=quizSearch.jsp");
            return;
        }
        req.getSession().setAttribute("quiz", quizToDisplay);
        req.getSession().setAttribute("questions", questionList);
        req.getSession().setAttribute("currQuestion", 0);
        int immediateCorrection = 0;
        if(quizToDisplay.isCorrect()) immediateCorrection = 1;
        if(quizToDisplay.isPages()) {
            resp.sendRedirect("QuizDisplay.jsp?immediate=" + immediateCorrection);
        } else {
            resp.sendRedirect("QuizDisplay2.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
