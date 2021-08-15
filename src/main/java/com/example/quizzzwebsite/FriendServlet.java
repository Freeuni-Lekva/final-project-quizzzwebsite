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
        User user = (User)request.getSession().getAttribute(User.ATTRIBUTE_NAME);
        String userName = user.getUserName();
        if(name != null){
            System.out.println(name);
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
        name = request.getParameter("removeRequest");
        if(name != null){
            try {
                UserRelationManager.removeRequest(name,userName);
                RequestDispatcher dispatcher = request.getRequestDispatcher("Friends.jsp");
                dispatcher.forward(request,response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }
        name = request.getParameter("removeFriend");
        if(name != null){
            try {
                UserRelationManager.removeFriendship(name,userName);
                RequestDispatcher dispatcher = request.getRequestDispatcher("Friends.jsp");
                dispatcher.forward(request,response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }
        name = request.getParameter("unsend");
        if(name != null){
            try{
                System.out.println(name);
                UserRelationManager.removeRequest(userName,name);
                RequestDispatcher dispatcher = request.getRequestDispatcher("Friends.jsp");
                dispatcher.forward(request,response);
            } catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}