<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="java.util.*"%>
<%@ page import="user.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String forAdmin="adminPage.jsp";
    String jspName="Home.jsp";

     User usr=(User)application.getAttribute(User.ATTRIBUTE_NAME);
     if(usr.isAdmin()) jspName=forAdmin;



%>
<head>
    <title>CREATE QUIZ</title>
    <link rel="stylesheet" href="creationStyle.css" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>

<body>

<div class="main_class">
    <div class="heading"><h1 >CREATE QUIZ</h1></div>
    <div class="panelBody">

        <form  action="Quiz-CreationServlet" method="post">

            <div class="Panel">
            <div class="leftBar">
                <h3 class="QuizName_header">Quiz Name:</h3>
                <input type="text" name="quizName" placeholder="Enter Quiz Name"> <br>
                <h3 class="Description_header">Quiz Description:</h3>
                <textarea class="descriptionField" rows="10" name="QuizDescription" id="QuizDescription"></textarea>
                <br>
                <div class="Correction_possibility">
                    <h4 class="Correction_header">Immediate Correction Mode</h4>
                    <input type="radio" name="correction" value="yes" checked> Yes<br>
                    <input type="radio" name="correction" value="no"> No <br>
                </div>
                <br>
                <div class="Practise Mode">
                    <h4 class="Practice_header">Practice Mode</h4>
                    <input type="radio" name="practice" value="yes"> Yes<br>
                    <input type="radio" name="practice" value="no" checked> No <br>
                </div>
                <br>
                <div class="Randomised">
                    <h4 class="Randomised_header">Random Mode</h4>
                    <input type="radio" name="random" value="yes"> Yes<br>
                    <input type="radio" name="random" value="no" checked> No <br>
                </div>


                <br>
                <div class="multiple_pages_possibility">
                    <h4 class="">Display Options</h4>
                    <input type="radio" name="pages" id="single" value="yes" checked> Single Page<br>
                    <input type="radio" name="pages" id="multiple" value="no"> Single Question per Page <br>
                </div>
                <br>
            </div>

            <div class="questionCreation">
                <br><h2 style="text-align:center;">Create Questions</h2><br/>
                <button type="button"  onclick="addFillInQuestion()">add fill in quiz.questions.question</button>
                <button type="button"   onclick="addMultipleChoiceQuestion()">add multiple choice
                    quiz.questions.question</button>
                <button type="button"   onclick="addPictureResponseQuestion()">add picture response
                    quiz.questions.question</button>
                <button type="button"  onclick="addResponseQuestion()">add response quiz.questions.question</button>

                <script>
                    var countBox =1;
                    var boxName = 0;
                    function addFillInQuestion()
                    {
                        var boxName="textBox"+countBox;
                        document.getElementById('response').innerHTML+='<h4>Fill In Questions</h4>'+'<br/>Input quiz.questions.question text and make  gap with "~"  symbol <input type="text" name="'+boxName+"11"+'"/><br>Input correct answers delimited with "~" symbol <input type="text" name="'+boxName+"21"+'"/><br/>';
                        countBox += 1;
                    }
                    function addMultipleChoiceQuestion()
                    {
                        var boxName="textBox"+countBox;
                        document.getElementById('response').innerHTML+='<h4>Multiple Choice Questions</h4>'+'<br/>Input quiz.questions.question text  <input type="text" name="'+boxName+"12"+'"/><br>Input correct answers delimited with "~" symbol <input type="text" name="'+boxName+"22"+'"/><br>Input probable answers delimited with "~" symbol <input type="text" name="'+boxName+"32"+'"/><br/>';
                        countBox += 1;
                    }
                    function addPictureResponseQuestion()
                    {
                        var boxName="textBox"+countBox;
                        document.getElementById('response').innerHTML+='<h4>Picture Response Question</h4>'+'<br/>Input URL of picture<input type="text" name="'+boxName+"13"+'"/><br>Input correct answer <input type="text" name="'+boxName+"23"+'"/><br/>';
                        countBox += 1;
                    }
                    function addResponseQuestion()
                    {
                        var boxName="textBox"+countBox;
                        document.getElementById('response').innerHTML+='<h4>Response Question</h4>'+'<br/>Input quiz.questions.question text <input type="text" name="'+boxName+"14"+'"/><br>Input correct answer <input type="text" name="'+boxName+"24"+'"/><br>Input probable answers delimited with "~" symbol <input type="text" name="'+boxName+"34"+'"/><br/>';
                        countBox += 1;
                    }
                </script>
                <span id="response"></span>
            </div>
            </div>
            <br>
            <button type="submit" class="btn" value="submit">Create</button>
            <a class="back" href="<%= jspName %>" >Back</a>
        </form>
    </div></div></div></div>

</body>


</html>