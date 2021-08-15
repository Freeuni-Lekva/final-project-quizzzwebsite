package com.example.quizzzwebsite;

import user.User;
import user.UserRelationManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FriendServlet", value = "/FriendServlet")
public class FriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("sendRequest");
        if(name != null){
            System.out.println(name);
            User user = (User)request.getSession().getAttribute(User.ATTRIBUTE_NAME);
            String userName = user.getUserName();
            try {
                UserRelationManager.sendRequest(userName,name);
                RequestDispatcher dispatcher = request.getRequestDispatcher("Friends.jsp");
                dispatcher.forward(request,response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }
        name = request.getParameter("requestorName");
        if(name != null){
            User user = (User)request.getSession().getAttribute(User.ATTRIBUTE_NAME);
            String userName = user.getUserName();
            try {
                UserRelationManager.removeRequest(name,userName);
                UserRelationManager.addFriendship(userName,name);
                RequestDispatcher dispatcher = request.getRequestDispatcher("Friends.jsp");
                dispatcher.forward(request,response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}