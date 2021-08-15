package com.example.quizzzwebsite;


import user.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "adminServlet", value = "/adminServlet")

public class adminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param;
        RequestDispatcher dispatcher = null;
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
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(User.ATTRIBUTE_NAME);
        String text = request.getParameter("text");
        String title = request.getParameter("title");
        if(text.length() > 0 && title.length() > 0) {
            try {
                AnnouncementManager.addAnnouncement(title, text, user.getUserName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                response.sendRedirect("Error.jsp?id=adminPage.jsp");
                return;
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminPage.jsp");
        dispatcher.forward(request,response);
    }
}