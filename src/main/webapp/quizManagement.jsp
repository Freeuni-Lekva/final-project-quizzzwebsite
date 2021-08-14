<%@ page import="com.example.quizzzwebsite.getQuiz" %>
<%@ page import="com.example.quizzzwebsite.quizDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/14/2021
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!Doctype html>
<html>
<head>
    <title>Quiz Management</title>
</head>
<body>
<h1> Manage Quizzes </h1>

<!-- will be added later
<form action="QuizManagementServlet" method="get" onsubmit="searchQuiz(); return false">
    <input id="quizName" name="quizName" type="text" placeholder="Enter Quiz name">
    <button type="submit" id="searchQuizzes"> Search </button>
</form>
<script type="text/javascript" src="searchQuizzes.js"></script> -->
<div id="quizTableContainer">
    <table id="quizzes" >
        <tr>
            <th> Quiz Name </th>
            <th> Practice Mode </th>
            <th> Creator Id </th>
            <th> Creation Date </th>
        </tr>
        <%
            List<getQuiz> list = null;
            try {
                list = quizDao.getAllQuizzes();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                response.sendRedirect("Error.jsp?id=adminPage.jsp");
            }
            if(list != null) {
                for (int i = 0; i < list.size(); i++) {
                    getQuiz temp = list.get(i);
                    out.println("<tr>");
                    out.println("<th>" + temp.getName() + "</th>");
                    out.println("<th>" + temp.isPractice() + "</th>");
                    out.println("<th>" + temp.getCreatorID() + "</th>");
                    out.println("<th>" + temp.getCreationTime() + "</th>");
                    out.println("<th> <form action=\"QuizManagementServlet\" method=\"get\">");
                    out.println("<input type=\"hidden\" name=\"quizId\" value=\"" + temp.getID() + "\">");
                    out.println("<input type=\"hidden\" name=\"buttonType\" value=\"removeButton\">");
                    out.println("<button name=\"removeQuiz\" type=\"submit\"> Remove Quiz </button> </th> </form>");
                    out.println("<th> <form action=\"QuizManagementServlet\" method=\"get\">");
                    out.println("<input type=\"hidden\" name=\"quizId\" value=\"" + temp.getID() + "\">");
                    out.println("<input type=\"hidden\" name=\"buttonType\" value=\"clearButton\">");
                    out.println("<button name=\"clearHistory\" type=\"submit\"> Clear History </button> </th> </form>");
                    out.println("</tr>");
                }
            }
        %>
    </table>
</div>
</body>
</html>
