package com.example.quizzzwebsite;

import user.User;
import user.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserManagementServlet", value = "/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String buttonType = request.getParameter("buttonType");

        if(buttonType == null){
            PrintWriter out = response.getWriter();
            try {
                List<User> list = UserManager.getUserByName(userName);

                String respText = "<table id=\"users\"> <tr> <th> Username </th> <th>" +
                        "user ID </th> <th> isAdmin </th> </tr>";

                for (int i = 0; i < list.size(); i++) {
                    User curr = list.get(i);

                    respText +=
                            "<tr>"
                            + "<th>" + curr.getUserName() + "</th>"
                            + "<th>" + curr.getId()+ "</th>"
                            + "<th>" + curr.isAdmin() + "</th>"
                            + "<th> <form action=\"UserManagementServlet\" method=\"get\">"
                            + "<input type=\"hidden\" name=\"userName\" value=\"" + curr.getUserName() + "\">"
                            + "<input type=\"hidden\" name=\"buttonType\" value=\"removeButton\">"
                            + "<button name=\"removeUser\" type=\"submit\"> Remove </button> </form> </th>"
                            + "<th> <form action=\"UserManagementServlet\" method=\"get\">"
                            + "<input type=\"hidden\" name=\"userName\" value=\"" + curr.getUserName() + "\">"
                            + "<input type=\"hidden\" name=\"buttonType\" value=\"promoteButton\">"
                            + "<button name=\"promoteUser\" type=\"submit\"> Promote </button> </form> </th>"
                            + "</tr>";
                }
                respText += "</table>";
                out.write(respText);
            }catch(SQLException throwables){
                throwables.printStackTrace();
                response.sendRedirect("Error.jsp?id=userManagement.jsp");
                return;
            }
        }else {
            if (buttonType.equals("removeButton")) {
                try {
                    UserManager.deleteUser(userName);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("Error.jsp?id=userManagement.jsp");
                    return;
                }
            } else {
                try {
                    UserManager.promoteUser(userName);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("Error.jsp?id=userManagement.jsp");
                    return;
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("userManagement.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
