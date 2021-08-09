<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<html>
<head>
    <link rel="stylesheet" href="loginstyle.css" >
</head>
<body>

<div class="Welcome">
    <div class="Sign-in-box">
        <label >Please Try Again!!</label><br>
        <form action="LoginServlet" method="post">
            <input type="email"  placeholder="Enter email" name="email"/>
            <input  type="password"  placeholder="Enter Password" name="password" />
            <button class="Sign-in" id="btn" type="submit" >Sign in</button>

        </form>
        <a href="Registration.jsp" >Create Account</a>
    </div>
</div>



</body>
</html>