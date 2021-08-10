package com.example.quizzzwebsite;

import user.ChatManager;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/MyChatServlet")
public class MyChatServlet extends HttpServlet {




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out=response.getWriter();
        String sender=request.getParameter("user");
        String receiver=request.getParameter("friend");
        String txt=request.getParameter("txt");
        try {
            ChatManager.add(sender,receiver,txt);
        } catch (SQLException | ClassNotFoundException throwables) {
            request.getRequestDispatcher("Error.jsp?id=Chat.jsp").forward(request,response);
        }
        out.append(null);
    }

   public void doPost(HttpServletRequest request, HttpServletResponse response) {

   }


}