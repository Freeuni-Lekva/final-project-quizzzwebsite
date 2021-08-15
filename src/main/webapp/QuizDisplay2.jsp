<%@ page import="com.example.quizzzwebsite.quiz" %>
<%@ page import="user.UserManager" %><%--
  Created by IntelliJ IDEA.
  User: lomid
  Date: 8/15/2021
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        quiz quizToDisplay = (quiz)request.getSession().getAttribute("quiz");
        String creatorName = UserManager.getUsernameById(quizToDisplay.getCreatorID());
    %>
    <title>Quiz</title>
    <script>
        function start() {
            document.getElementById("outerDiv").innerHTML = "";
            addQuestion();
            document.getElementById("nextbutton").value = "submit";
            document.getElementById("nextbutton").onclick = submit;
        }
        function addQuestion() {
            var xhr = null;
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "GetQuestioServlet";
            xhr.onreadystatechange =
                function () {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            if(xhr.responseText == "-1") return;
                            document.getElementById("container").insertAdjacentHTML("beforeend", xhr.responseText);
                            addQuestion();
                        }
                    }
                }
            xhr.open("GET", url, true);
            xhr.send(null);
        }
        function submit() {
            var questions = document.getElementsByClassName("container");
            var answers = [];
            Array.prototype.forEach.call(questions, function(question) {
                var type = "";
                for (var i = 0; i < question.childNodes.length; i++) {
                    if (question.childNodes[i].className == "type") {
                        type = question.childNodes[i].value;
                        break;
                    }
                }
                var answer = "";
                if(type == "1") {
                    answer = getFillBlankAnswer(question);
                } else if(type == "2") {
                    answer = getMultipleChoiceAnswer(question);
                } else if(type == "3") {
                    answer = getPictureResponseAnswer(question);
                } else if(type == "4") {
                    answer = getQuestionResponseAnswer(question);
                }
                answers.push(answer);
            });
            submitAnswers(answers);
        }
        function submitAnswers(answers) {

        }
        function getFillBlankAnswer(question) {
            var answer = "";
            for (var i = 0; i < question.childNodes.length; i++) {
                if (question.childNodes[i].className == "blank") {
                    answer += question.childNodes[i].value;
                    answer += "~";
                }
            }
            return answer;
        }
        function getMultipleChoiceAnswer(question) {
            var answer = "";
            for (var i = 0; i < question.childNodes.length; i++) {
                if (question.childNodes[i].className == "checkBox") {
                    answer += question.childNodes[i].value;
                    answer += "~";
                }
            }
            return answer;
        }
        function getPictureResponseAnswer(question) {
            var answer = "";
            for (var i = 0; i < question.childNodes.length; i++) {
                if (question.childNodes[i].id == "answer") {
                    answer += question.childNodes[i].value;
                    break;
                }
            }
            return answer;
        }
        function getQuestionResponseAnswer(question) {
            var answer = "";
            for (var i = 0; i < question.childNodes.length; i++) {
                if (question.childNodes[i].className == "radiobutton") {
                    if(question.childNodes[i].checked) {
                        answer += question.childNodes[i].value;
                        answer += "~";
                    }
                }
            }
            return answer;
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
    <button id="nextbutton" onclick="next()"> start </button>
</div>

</body>
</html>
