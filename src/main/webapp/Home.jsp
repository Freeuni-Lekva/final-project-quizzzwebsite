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
                        <li><a href="#">Profile</a></li>
                        <li><a href="#">Friends</a></li>
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
            <li><a href="#">About Us</a></li>
            <li><a href="Welcome.jsp">Sign out</a></li>
        </ul>
    </div>

<div class="boxes">
    <div class="leaderboard">
        <div class="title1"> Leaderboard</div>
        <ul>

            <%  //example
                out.println("<li>"+"petre"+"<p>"+"1988"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"198"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1688"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1588"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1488"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1388"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1288"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1188"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"1088"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"988"+"</p></li>");
                out.println("<li>"+"petre"+"<p>"+"88"+"</p></li>");


            /* get leaderboard and println as it is given in example
                */
            %>
        </ul>
    </div>
    <div class="Announcements">
        <div class="title2"> Announcements</div>
        <ul>
        <%  //example
            out.println("<li>"+" News..."+"</li>");

            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            out.println("<li>"+"News..."+"</li>");
            /* get Announcements and println as it is given in example
             */
        %>
        </ul>
     </div>
</div>


</body>
</html>