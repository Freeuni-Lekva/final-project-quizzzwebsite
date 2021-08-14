package com.example.quizzzwebsite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "adminServlet", value = "/adminServlet")

public class adminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param;
        RequestDispatcher dispatcher = null;
        param = request.getParameter("announcement");
        if (param != null) {
            return;
        }
        param = request.getParameter("quizManagementButton");
        if (param != null) {
            dispatcher = request.getRequestDispatcher("quizManagement.jsp");
            dispatcher.forward(request, response);
        }
        param = request.getParameter("userManagementButton");
        if (param != null) {
            dispatcher = request.getRequestDispatcher("userManagement.jsp");
            dispatcher.forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}