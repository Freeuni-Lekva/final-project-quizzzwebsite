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
<div class="requests">
    <label>
        you have got requests from:
        <br>
        <br>
    </label>
    <%
        out.println("<ul id=\"recRequests\">");
        User tmp= (User) request.getSession().getAttribute(User.ATTRIBUTE_NAME);
        List<String> list = null;
        try {
            list = UserRelationManager.getReceivedRequestList(tmp.getUserName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(list != null){
            if(list.size() == 0){
                out.println("<li> No Requests Received </li>");
            }
            for(int i = 0;i < list.size();i++){
                out.println("<li>"
                            + list.get(i)
                            + "<form action=\"FriendServlet\" method=\"get\">"
                            + "<input type=\"hidden\" name=\"requestorName\" value=\"" + list.get(i) + "\">"
                            + "<button type=\"submit\" id=\"acceptRequest\" name=\"acceptRequest>\"> Accept Request </button>"
                            + "<form>"
                        + "</li>");
            }
        }
        out.println("</ul>");
    %>
</div>
<div class="friends-bar">
    <ul>

        <%
            List<String> friends = null;
            try {
                friends = UserRelationManager.getFriendList(tmp.getUserName());
            } catch (SQLException throwables) {
                request.getRequestDispatcher("Error.jsp?id=Friends.jsp");
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

<div class="otherUsers" id="otherUsers">
    <ul id="foundUsers">
    </ul>
    <form action="UserManagementServlet" method="get" onsubmit="searchUsers(); return false">
        <input id="potFriendName" name="potFriendName" type="text" placeholder="Enter Username">
        <button type="submit" id="searchButton"> Search </button>
    </form>
    <script type="text/javascript">
        function searchUsers() {
            let userlist = document.getElementById("foundUsers");
            let parent = userlist.parentElement;
            parent.removeChild(userlist);
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
            let url = "UserManagementServlet?potFriendName=" + document.getElementById("potFriendName").value;
            req.open("get", url);
            req.send(null);

            function handler() {
                if (req.readyState == 4) {
                    if (req.status == 200) {
                        document.getElementById("otherUsers").insertAdjacentHTML("beforeend", req.responseText);
                    } else {
                        alert("ajax erred my friend");
                    }
                }
            }
        }
    </script>
</div>
</body>
</html>