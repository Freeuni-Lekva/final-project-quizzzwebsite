
const form=document.getElementById("form");




var interval=window.setInterval(function () {
    displayFriendMessage()
}  ,1000);

form.addEventListener("click",e =>{
    e.preventDefault()
    sendMessage()

} )

form.addEventListener("submit",e =>{
    e.preventDefault()
    sendMessage()

} )

function sendMessage(){
    const txt=document.getElementById("txt").value;
   if(txt==="") return;
   addToData(txt);

};

function displayMyMessage(){
    const txt=document.getElementById("txt").value;
    const html='<div class="my-chat">'+txt+'</div>';
    document.getElementById("chats").innerHTML+=html;

};


var xhr=null;
function addToData(txt){

    try{
        xhr=new XMLHttpRequest();
    }catch(e){
        xhr=new ActiveXObject("Microsoft.XMLHTTP");
    }

    if(xhr==null){
        alert("Ajax not Supported!!");
        return;
    }
    var url="MyChatServlet?friend="+document.request.getParameter("friend")+"&user="+document.request.getParameter("user")+
        "&txt="+txt;
    xhr.onreadystatechange=check1;
    xhr.open("GET",url,true);
    xhr.send(null);

}

function check1(){
    if(xhr.readyState===4){
        if(xhr.status===200){
            displayMyMessage();
            document.getElementById("txt").value="";
        }else{
            alert("Error");
        }
    }
}

var xhr2=null;
function displayFriendMessage() {
    try{
        xhr2=new XMLHttpRequest();
    }catch(e){
        xhr2=new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(xhr2==null){
        alert("Ajax not Supported!!");
        return;
    }
    var url="SenderChatServlet?friend="+document.request.getParameter("friend")+"&user="+document.request.getParameter("user");
    xhr2.onreadystatechange=check2;
    xhr2.open("GET",url,true);
    xhr2.send(null);
}

function check2(){
    if(xhr2.readyState===4){
        if(xhr2.status===200){
            const html='<div class="sender-chat">'+xhr.responseText+'</div>';
            document.getElementById("chats").innerHTML+=html;
        }else{
            alert("Error");
        }
    }
}