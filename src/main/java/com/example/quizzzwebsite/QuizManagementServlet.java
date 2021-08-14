package com.example.quizzzwebsite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "QuizManagementServlet", value = "/QuizManagementServlet")
public class QuizManagementServlet extends HttpServlet {@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String buttonType = request.getParameter("buttonType");
    if (buttonType == null) {
        PrintWriter out = response.getWriter();
        String quizName = request.getParameter("quizName");
        try {
            List<getQuiz> list = quizDao.getQuizByName(quizName);
            String respText = "<table id=\"quizzes\"> <tr> <th> Quiz Name </th> <th>" +
                    "Practice Mode </th> <th> Creator Id </th> <th> Creation Date </th> </tr>";

            for (int i = 0; i < list.size(); i++) {
                getQuiz curr = list.get(i);
                respText += "<tr>"
                        + "<th>" + curr.getName() + "</th>"
                        + "<th>" + curr.isPractice() + "</th>"
                        + "<th>" + curr.getCreatorID() + "</th>"
                        + "<th>" + curr.getCreationTime() + "</th>"
                        + "<th> <form action=\"QuizManagementServlet\" method=\"get\">"
                        + "<input type=\"hidden\" name=\"quizId\" value=\"" + curr.getID() + "\">"
                        + "<input type=\"hidden\" name=\"buttonType\" value=\"removeButton\">"
                        + "<button name=\"removeQuiz\" type=\"submit\"> Remove Quiz </button> </th>"
                        + "</form>"
                        + "<th> <form action=\"QuizManagementServlet\" method=\"get\">"
                        + "<input type=\"hidden\" name=\"quizId\" value=\"" + curr.getID() + "\">"
                        + "<input type=\"hidden\" name=\"buttonType\" value=\"clearButton\">"
                        + "<button name=\"clearHistory\" type=\"submit\"> Clear History </button> </th>"
                        + "</form>"
                        + "</tr>";
            }
            respText += "</table>";
            out.write(respText);
        }catch(SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
    } else {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        if (buttonType.equals("removeButton")) {
            try {
                quizDao.remove(quizId);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                response.sendRedirect("Error.jsp?id=quizManagement.jsp");
            }
        } else if (buttonType.equals("clearButton")) {
            try {
                quizDao.removeRecordsByQuizID(quizId);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                response.sendRedirect("Error.jsp?id=quizManagement.jsp");
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("quizManagement.jsp");
        dispatcher.forward(request, response);
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
