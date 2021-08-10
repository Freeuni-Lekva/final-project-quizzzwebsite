package com.example.quizzzwebsite;

import user.ChatManager;
import user.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;



@WebServlet("/SenderChatServlet")
public class SenderChatServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out=response.getWriter();
        String receiver=request.getParameter("user");
        String sender=request.getParameter("friend");
        List<Message> messageList=null;
        try {
            messageList= ChatManager.getUnseen(sender,receiver);
            ChatManager.changeUnseen(sender,receiver);
        } catch (SQLException | ClassNotFoundException throwables) {
            request.getRequestDispatcher("Error.jsp?id=Chat.jsp").forward(request,response);
        }
        if(messageList!=null && messageList.size()>0){
            out.append( messageList.get(0).getMessage());
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
