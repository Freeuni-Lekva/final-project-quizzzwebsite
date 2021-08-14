function searchQuiz() {
    let table = document.getElementById("quizzes");
    let parent = table.parentElement;
    parent.removeChild(table);
    let req = null;
    try{
        req = new XMLHttpRequest();
    }catch(e){
        req = new ActiveXObject();
    }
    if(req == null){
        alert("ajax not supported");
    }
    req.onreadystatechange = handler;
    let url = "QuizManagementServlet?quizName=" + document.getElementById("quizName").value;
    req.open("get",url);
    req.send(null);

    function handler(){
        if(req.readyState == 4){
            if(req.status == 200){
                document.getElementById("quizTableContainer").insertAdjacentHTML("beforeend",req.responseText);
            }else{
                alert("ajax not supported");
            }
        }
    }
};
