
<%@ page import="java.util.List" %>

<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="Error.css">
</head>
<body>
<div class="text">
<h1>Something went wrong</h1>

<a <%
    out.println("href=\""+request.getParameter("id")+"\"");
%> >Please try again</a>
</div>
</body>
</html>