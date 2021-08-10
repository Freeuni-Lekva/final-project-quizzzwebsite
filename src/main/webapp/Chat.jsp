<%@ page import="java.util.List" %>
<%@ page import="user.User" %>
<%@ page import="user.UserRelationManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" href="Chatstyle.css" >
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
                    <li><a href="#"> Create Quiz</a></li>
                    <li><a href="#">Write a Quiz</a></li>
                </ul>
            </div>
        </li>
        <li><a href="AboutUs.jsp">About Us</a></li>
        <li><a href="Welcome.jsp">Sign out</a></li>
    </ul>
</div>

<div class="friends-bar">
    <ul>

        <%User tmp= (User) request.getSession().getAttribute(User.ATTRIBUTE_NAME);

            List<String> friends = null;
            try {
                friends = UserRelationManager.getFriendList(tmp.getUserName());
            } catch (SQLException throwables) {
                request.getRequestDispatcher("Error.jsp?id=Chat.jsp");
            }
            if(friends!=null) {
                if(friends.size()>0) {
                    for (String curr : friends) {
                        out.println("<li><a href=\"Messages.jsp?friend=" + curr + "&user=" + tmp.getUserName() + "\">" + curr + "</a></li>");
                    }
                }else{
                    out.println("<li>"+"No Friends"+"</li>");
                }

            }
        %>

    </ul>

</div>


</body>
</html>