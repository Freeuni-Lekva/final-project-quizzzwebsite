<%@ page import="user.UserManager" %>
<%@ page import="com.example.quizzzwebsite.quizDao" %>
<%@ page import="com.example.quizzzwebsite.AnnouncementManager" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/14/2021
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Admin Homepage</title>
</head>
<body>
<h1>Admin Page</h1>
<br>
<div id="siteStatistics">
  <label> Site Statistics </label>
  <br>
  <br>
  <table name="statistics">
    <tr>
      <th> Number of Users </th>
      <th> Number of Quizzes </th>
      <th> Number of Quiz attempts </th>
      <th> Number of Announcements </th>
    </tr>
    <tr>
      <th><%=UserManager.getNumUsers()%></th>
      <th><%=quizDao.getNumQuizzes()%></th>
      <th><%=quizDao.getNumAttempts()%> </th>
      <th><%=AnnouncementManager.getNumAnnouncements()%></th>
    </tr>
  </table>
</div>
<br>
<div id="announcements">
  <h1>Create an Announcement</h1>

  <form action="adminServlet" method="post">
    <br>
    <input name="title" placeholder="Enter title">
    <br>
    <br>
    <textArea name="text" cols = "20" rows = "10" placeholder="Enter text here"></textArea>
    <br>
    <button type="submit"> Announce </button>
  </form>
</div>

<br>

<div id="userManagement">
  <label> Manage Users </label>
  <br>
  <br>
  <form action="adminServlet" method="get">
    <button name = "userManagementButton" type = "submit"> Manage users </button>
  </form>
</div>

<div id="quizManagement">
  <label> Manage Quizzes </label>
  <br>
  <br>
  <form action="adminServlet" method="get">
    <button name = "quizManagementButton" type = "submit"> Manage quizzes </button>
  </form>
</div>
</body>
</html>
