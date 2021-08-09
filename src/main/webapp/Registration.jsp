
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create account</title>
    <link rel="stylesheet" href="Registrationstyle.css" >
</head>
<body>
    <div class="container">
        <form action="RegistrationServlet" method="post">
            <label class="Registration">Registration</label><br>
            <input type="text" id="name" name="fullname" placeholder="Enter Fullname">

            <input type="email" id="email" name="email" placeholder="Enter Email">

            <input type="password" id="password" name="password" placeholder="Enter Password">
            <button type="submit"  class="register">Register</button>
        </form>
        <a href="../../../../OOPProject/src/main/webapp/Welcome.jsp" >Sign in</a>
    </div>
    <a href="index.jsp">login</a>
</body>
</html>
