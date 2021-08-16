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
    <title>Quiz222</title>

</head>
<body>
<div id="outerDiv">
    <div id="container">
        <h3><%=quizToDisplay.getName()%></h3>
        <p><%=quizToDisplay.getDescription()%></p>
        created by <%=creatorName%>
    </div>
    <button id="nextbutton" onclick="startt()"> start </button>
</div>
<script>

    function addQuestion() {
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
                        if(xhr.responseText != "-1") {
                            document.getElementById("container").insertAdjacentHTML("beforeend", xhr.responseText);
                            addQuestion();
                        }
                    }
                }
            }
        xhr.open("GET", url, true);
        xhr.send(null);
    }

    function tellServletAndSubmit(answers) {
        document.getElementById("nextbutton").innerText = "insubmit2";
        var xhr = null;
        try {
            xhr = new XMLHttpRequest();
        } catch (e) {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var url = "SubmitAnswerServlet?startsubmitting=1";
        xhr.onreadystatechange =
            function () {
                if (xhr.readyState == 4) {
                    alert(xhr.responseText);
                    if (xhr.status == 200) {
                        if(xhr.responseText == "ok") {
                            submitAnswers(answers, 0);
                        }
                    }
                }
            }
        xhr.open("GET", url, true);
        xhr.send(null);

    }

    function submittt() {
        document.getElementById("container").innerHTML = "";
        var questions = document.getElementsByClassName("container");
        var answers = [];
        for (var i = 0; i < questions.length; i++) {
            var question = questions[i];
            var type = "";
            for (var i = 0; i < question.childNodes.length; i++) {
                if (question.childNodes[i].className == "type") {
                    type = question.childNodes[i].value;
                    break;
                }
            }
            document.getElementById("nextbutton").value = type;
            var answer = "";
            if (type == "1") {
                answer = getFillBlankAnswer(question);
            } else if (type == "2") {
                answer = getMultipleChoiceAnswer(question);
            } else if (type == "3") {
                answer = getPictureResponseAnswer(question);
            } else if (type == "4") {
                answer = getQuestionResponseAnswer(question);
            }
            answers.push(answer);
        }
        tellServletAndSubmit(answers);
    }
    function startt() {
        document.getElementById("container").innerHTML = "";
        addQuestion();
        document.getElementById("nextbutton").innerText = "submit";
        document.getElementById("nextbutton").onclick = submittt;

    }

    function showSummary() {
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

    function submitAnswers(answers, index) {
        if(index >= answers.length) showSummary();
        var xhr = null;
        try {
            xhr = new XMLHttpRequest();
        } catch (e) {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var url = "SubmitAnswerServlet?" + "answer=" + answers[index];
        xhr.onreadystatechange =
            function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        if(xhr.responseText == "ok") {
                            submitAnswers(answers, index+1);
                        }
                    }
                }
            }
        xhr.open("GET", url, true);
        xhr.send(null);
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
</body>
</html>