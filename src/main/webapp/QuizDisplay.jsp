<%@ page import="com.example.quizzzwebsite.quiz" %>
<%@ page import="com.example.quizzzwebsite.question" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.quizzzwebsite.quizDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="user.User" %>
<%@ page import="user.UserManager" %><%--
  Created by IntelliJ IDEA.
  User: lomid
  Date: 8/14/2021
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz</title>
    <%
        quiz quizToDisplay = (quiz)request.getSession().getAttribute("quiz");
        String creatorName = UserManager.getUsernameById(quizToDisplay.getCreatorID());
    %>
    <script>
        function findGetParameter(parameterName) {
            var result = null,
                tmp = [];
            var items = location.search.substr(1).split("&");
            for (var index = 0; index < items.length; index++) {
                tmp = items[index].split("=");
                if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
            }
            return result;
        }
        var immediate = findGetParameter("immediate");
        function submitt() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet?type=1";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            if(xhr.responseText == "1") {
                                submitFillBlank();
                            } else if(xhr.responseText == "2") {
                                submitMultipleChoice();
                            } else if(xhr.responseText == "3") {
                                submitPictureResponse();
                            } else if(xhr.responseText == "4") {
                                submitQuestionResponse();
                            }
                        }
                    }
                }
            xhr.open("GET", url, true);
            xhr.send(null);
        }
        function submitFillBlank() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet?type=1";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            document.getElementById("container").innerHTML = "";
                            if(immediate == "1") {
                                displayQuestionMark(xhr.responseText);
                            } else if(xhr.responseText == "ok") {
                                displayNextQuestion();
                            }
                        }
                    }
                }
            xhr.open("POST", url, true);
            var blanks = document.getElementsByClassName("blank");
            var answers = "";
            Array.prototype.forEach.call(blanks, function(blank) {
                answers += blank.value();
                answers += "~";
            });
            xhr.send("answer=" + answers);
        }
        function submitMultipleChoice() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            document.getElementById("container").innerHTML = "";
                            if(immediate == "1") {
                                displayQuestionMark(xhr.responseText);
                            } else if(xhr.responseText == "ok") {
                                displayNextQuestion();
                            }
                        }
                    }
                }
            var boxes = document.getElementsByClassName("checkBox");
            var answers = "";
            Array.prototype.forEach.call(boxes, function(box) {
                if(box.checked()) {
                    answers += box.value();
                    answers += "~";
                }
            });
            xhr.open("POST", url, true);
            xhr.send("answer=" + answers);
        }
        function submitPictureResponse() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            document.getElementById("container").innerHTML = "";
                            if(immediate == "1") {
                                displayQuestionMark(xhr.responseText);
                            } else if(xhr.responseText == "ok") {
                                displayNextQuestion();
                            }
                        }
                    }
                }
            var answer = document.getElementById("answer");
            xhr.open("POST", url, true);
            xhr.send("answer=" + answer);
        }
        function submitQuestionResponse() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            document.getElementById("container").innerHTML = "";
                            if(immediate == "1") {
                                displayQuestionMark(xhr.responseText);
                            } else if(xhr.responseText == "ok") {
                                displayNextQuestion();
                            }
                        }
                    }
                }
            var radiobuttons = document.getElementsByClassName("radiobutton");
            var answer = "";
            Array.prototype.forEach.call(buttons, function(button) {
                if(button.checked()) {
                    answer += button.value();
                }
            });
            xhr.open("POST", url, true);
            xhr.send("answer=" + answer);
        }
        function displayNextQuestion() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            if(xhr.responseText == "-1") {
                                displaySummary();
                                return;
                            }
                            document.getElementById("container").insertAdjacentHTML("afterbegin", xhr.responseText);
                            document.getElementById("nextbutton").onclick = submitt;
                            var nextlabel = "next";
                            if(immediate) nextlabel = "submit";
                            document.getElementById("nextbutton").value = nextlabel;
                        }
                    }
                }
            xhr.open("GET", url, true);
            document.getElementById("container").innerHTML = "";
            xhr.send(null);
        }
        function displaySummary() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestionServlet?summary=1";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            var div = "<div class=\"container\"> " + xhr.responseText + "</div>";
                            document.getElementById("container").insertAdjacentHTML("afterbegin", div);
                        }
                    }
                }
            xhr.open("GET", url, true);
            document.getElementById("container").innerHTML = "";
            xhr.send(null);
        }
        function displayQuestionMark(mark) {
            var div = "<div class=\"container\"> <h2> " + mark + " points on that question</h2></div>";
            document.getElementById("container").insertAdjacentHTML("afterbegin", div);
            document.getElementById("nextbutton").value = "next";
            document.getElementById("nextbutton").onclick = displayNextQuestion;
        }
    </script>
</head>
<body>
<div id="outerDiv">
    <div id="container">
        <h3><%=quizToDisplay.getName()%></h3>
        <p><%=quizToDisplay.getDescription()%></p>
        created by <%=creatorName%>
    </div>
    <button id="nextbutton" onclick="displayNextQuestion()"> start </button>
</div>
</body>
</html>