<%@ page import="user.UserManager" %>
<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/14/2021
  Time: 8:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>User Management</title>
</head>
<body>
<h1> Manage Users </h1>
<br>
<a id="backButton" href="adminPage.jsp" >Back</a>
<br>
<br>
<form action="UserManagementServlet" method="get" onsubmit="searchUser(); return false">
  <input id="userName" name="userName" type="text" placeholder="Enter User Name">
  <button type="submit" id="searchUsers"> Search </button>
</form>

<script type="text/javascript">
  function searchUser() {
    console.log("it really logged something");
    let table = document.getElementById("users");
    let parent = table.parentElement;
    parent.removeChild(table);
    let req = null;
    try {
      req = new XMLHttpRequest();
    } catch (e) {
      req = new ActiveXObject();
    }
    if (req == null) {
      alert("ajax not supported");
    }
    req.onreadystatechange = handler;
    let url = "UserManagementServlet?userName=" + document.getElementById("userName").value;
    req.open("get", url);
    req.send(null);

    function handler() {
      if (req.readyState == 4) {
        if (req.status == 200) {
          //document.getElementById("usersTableContainer").insertAdjacentHTML("beforeend","<button> somebutton </button>");
          document.getElementById("usersTableContainer").insertAdjacentHTML("beforeend", req.responseText);
        } else {
          alert("ajax erred my friend");
        }
      }
    }
  }
</script>
<div id="usersTableContainer">
  <table id="users">
    <tr>
      <th>Username</th>
      <th>UserID</th>
      <th>isAdmin</th>
    </tr>
    <%
      List<User> users = null;
      try {
        users = UserManager.getUsers(20);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
        response.sendRedirect("Error.jsp?id=adminPage.jsp");
        return;
      }
      if(users != null) {
        for (int i = 0; i < users.size(); i++) {
          User temp = users.get(i);
          out.println("<tr>");
          out.println("<th>" + temp.getUserName() + "</th>");
          out.println("<th>" + temp.getId() + "</th>");
          out.println("<th>" + temp.isAdmin() + "</th>");
          out.println("<th> <form action=\"UserManagementServlet\" method=\"get\">");
          out.println("<input type=\"hidden\" name=\"userName\" value=\"" + temp.getUserName() + "\">");
          out.println("<input type=\"hidden\" name=\"buttonType\" value=\"removeButton\">");
          out.println("<button name = \"button\" type = \"submit\"> Remove </button> </form> </th>");
          out.println("<th> <form action=\"UserManagementServlet\" method=\"get\">");
          out.println("<input type=\"hidden\" name=\"userName\" value=\"" + temp.getUserName() + "\">");
          out.println("<input type=\"hidden\" name=\"buttonType\" value=\"promoteButton\">");
          out.println("<button name = \"button\" type = \"submit\"> Promote </button> </form> </th>");
          out.println("</tr>");
        }
      }
    %>
  </table>
</div>
</body>
</html>
