<%@ page import="com.example.quizzzwebsite.Announcement" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.quizzzwebsite.AnnouncementManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.quizzzwebsite.quizDao" %>
<%@ page import="com.example.quizzzwebsite.getQuiz" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <link rel="stylesheet" href="Homestyle.css" >
    <title>Homepage</title>
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

<div class="boxes">
    <div class="leaderboard">
        <div class="title1"> Leaderboard</div>
        <ul>
            <%
                List<getQuiz> quizList = null;
                try {
                    quizList = quizDao.getQuizzes(10);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(quizList != null){
                    if(quizList.size() == 0){
                        out.println("<li> there are no quizzes </li>");
                    }else{
                        for(int i = 0;i < quizList.size();i++){
                            getQuiz quiz = quizList.get(i);
                            out.println("<li> Name: "
                                        + quiz.getName() + "<br>"
                                        + "CreatorId: " + quiz.getCreatorID() + "<br>"
                                        + "Description: " + quiz.getDescription() + "<br>"
                                        + "</li>");
                        }
                    }
                }
            %>
        </ul>
    </div>
    <div class="Announcements">

        <div class="title2"> Announcements</div>
        <ul>
            <%
                List<Announcement> list = null;
                try {
                    list = AnnouncementManager.getAnnouncements(10);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    response.sendRedirect("Error.jsp?id=QuizCreationPage.jsp");
                    return;
                }
                if(list != null) {
                    for(int i = 0;i < list.size();i++) {
                        out.println("<li> Title: " + list.get(i).getTitle());
                        out.println("<br>");
                        out.println("Announcement: ");
                        out.println(list.get(i).getText());
                        out.println("</li>");
                    }
                }
            %>
        </ul>
     </div>
</div>


</body>
</html>