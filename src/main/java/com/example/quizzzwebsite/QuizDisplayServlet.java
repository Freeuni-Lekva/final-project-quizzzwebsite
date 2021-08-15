package com.example.quizzzwebsite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("QuizDisplayServlet")
public class QuizDisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
