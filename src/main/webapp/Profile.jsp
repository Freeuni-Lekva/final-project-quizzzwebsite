<%@ page import="user.User" %>
<%@ page import="user.UserRelationManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.quizzzwebsite.quizDao" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="Profilestyle.css" >
</head>
<body>

<div class="Homepage-bar">
    <ul>
        <li><a href="Home.jsp">Home</a>
        <li><a href="#">Personal Info</a>
            <div class="info">
                <ul>
                    <li><a href="Profile.jsp">Profile</a></li>
                    <li><a href="Friends.jsp">Friends</a></li>
                </ul>
            </div>
        </li>
        <li><a href="Chat.jsp"> Chat</a></li>
        <li><a href="#">Quizzes</a>
            <div class="quizzes">
                <ul>
                    <li><a href="QuizCreationPage.jsp"> Create Quiz</a></li>
                    <li><a href="#">Write a Quiz</a></li>
                </ul>
            </div>
        </li>
        <li><a href="AboutUs.jsp">About Us</a></li>
        <li><a href="Welcome.jsp">Sign out</a></li>
    </ul>
</div>

<div class="profile">
    <h1><%
        User tmp= (User) request.getSession().getAttribute(User.ATTRIBUTE_NAME);
        out.println(tmp.getUserName());
    %></h1>
    <label>Total Score: <%=quizDao.getUserTotalScore(tmp.getId())%></label><br>
    <label>Number Of Created Quizzes: <%=quizDao.getUserQuizzes(tmp.getId()).size()%></label><br>
    <label>Number of Written Quizzes: <%=quizDao.getRecordsByUserID(tmp.getId()).size()%></label><br>
    <label>Number of Friends: <%=UserRelationManager.getFriendList(tmp.getUserName()).size() %></label><br>
    <label>Achievements : </label><br>
</div>
</body>
</html>
