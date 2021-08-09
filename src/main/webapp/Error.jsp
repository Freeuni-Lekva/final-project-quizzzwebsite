
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="Error.css">
</head>
<body>
<h1>Something went wrong</h1>

<a <%
    out.println("href=\""+request.getParameter("id")+"\"");
%> >Please try again</a>

</body>
</html>