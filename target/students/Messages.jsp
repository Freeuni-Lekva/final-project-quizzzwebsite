
<%@ page import="java.sql.SQLException" %>
<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="user.UserRelationManager" %>
<%@ page import="user.Message" %>
<%@ page import="user.ChatManager" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Messages</title>
<link rel="stylesheet" href="MessagesStyle.css" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


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


 <div class="container">

     <div class="friends-bar">
         <ul>

             <%
                 User tmp= (User) request.getSession().getAttribute(User.ATTRIBUTE_NAME);

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



    <div class="chat-box">

        <div class="friend">

            <div class="friend-info">
              <h3><%
                  out.print(request.getParameter("friend"));
              %></h3>

            </div>

        </div>

        <div class="chats" id="chats">
             <%

                 String friend=request.getParameter("friend");
                 String user=request.getParameter("user");
                 List<Message> chat= null;
                 try {
                     chat = ChatManager.getChat(user,friend);
                     if(chat.size()>0) ChatManager.changeUnseen(friend,user);
                 } catch (SQLException throwables) {
                     request.getRequestDispatcher("Error.jsp?id=Chat.jsp>").forward(request,response);
                 } catch (ClassNotFoundException e) {
                     request.getRequestDispatcher("Error.jsp?id=Chat.jsp").forward(request,response);
                 }

                 if(chat!=null) {
                     for (Message curr : chat) {
                         if (curr.getSender().equals(user)) {
                             out.println("<div class=\"my-chat\">" + curr.getMessage() + "<div>");
                         } else {
                             out.println("<div class=\"sender-chat\">" + curr.getMessage() + "<div>");
                         }
                     }
                 }
             %>
        </div>
        <div class="txt-input">
            <form id="form" >
               <input type="text"  placeholder="Enter Message" id="txt"/>
               <button  class="send-button" id="btn" type="submit" >send</button>
            </form>
        </div>

    </div>

 </div>
<script src="MessagesScript.js" type="module"></script>

</body>
</html>