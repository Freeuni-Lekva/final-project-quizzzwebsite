<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="user.UserRelationManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Friends</title>
    <link rel="stylesheet" href="Friends.css" >
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
        <li><a href="#"> Chat</a></li>
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

        <%

            User tmp= (User) request.getSession().getAttribute(User.ATTRIBUTE_NAME);

            List<String> friends = null;
            try {
                friends = UserRelationManager.getFriendList(tmp.getUserName());
            } catch (SQLException throwables) {
                request.getRequestDispatcher("Error.jsp?id=\"Friends.jsp\"");
            }
            if(friends!=null) {
                if(friends.size()!=0) {
                    for (String curr : friends) {
                        out.println("<li><a href=\"FriendsProfile.jsp?id=" + curr + "\">" + curr + "</a><button type=\"submit\" id=" + curr + "\">Remove</button></li>");
                    }
                }else{
                    out.println("<li>"+"No friends"+"</li>");
                }
            }
        %>
    </ul>

</div>

<div class="search">
    <form >
        <input type="text" placeholder="Search Friend"/>
        <button type="submit"  >Search</button>

    </form>

</div>

</body>
</html>