<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add</title>
<%
String root = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=root%>/css/header.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/css/upbit.css">
<style type="text/css">
h2{
	text-align: center;
}
#upbit{
    margin-left: 150px;
}
#content{
    padding: 20px;
    margin: 0px auto;
}
#content input,textarea{
    margin-left: 10px;
    padding: 10px;
    border: 1px solid #999;
    border-radius: 10px;
    box-shadow: 3px 3px 10px #e6e6e6;
}
#content #user input{
    width: 25%;
    margin-right: 50px;
}
#content #title input{
    margin-top: 20px;
    margin-bottom: 20px;
    width: 85%;
}
#content #msg textarea{
    min-width: 85%;
    max-width: 85%;
    min-height: 200px;
    max-height: 200px;
    box-shadow: inset 3px 3px 10px #e6e6e6;

    vertical-align: top;
}
#content input[type="file"]{
    border: none;
    box-shadow: none;
    padding: 10px;
    margin-left: 40px;
}
#content #btn {
    position: relative;
    width: 200px;
    margin-left: 900px;
}
#content #btn button{
    background-color: #036;
    padding: 10px;
    color: #fff;
    border-radius: 10px;
}
#footer{
	margin-top: 60px;
	clear: both;
	background-color: gray;
}
</style>
<script type="text/javascript" src="<%=root%>/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function (){
    $('#upbit').load("./bitslide.html");
});

$(function(){
	$('#content')
		.find('input')
			.eq(0).focus()
			.end()
		.end()
		.find('button')
			.last().click(function(){
				history.back();
				});
	$('#content').on('submit', 'form', function(e){
		e.preventDefault();
		insertOne(e.target);
	})

});

function insertOne(target){
    var param= {
    			bbsTitle:$('#bbsTitle').val(),
    			bbsContent:$('#bbsContent').val()
    			}
    $.post('http://192.168.99.100:8888/bbsproject/api/list',$.param(param),function(data){
        console.log('success',data);
        location.href='http://192.168.99.100:8080/bbsproject/index.jsp';
    });
}
</script>

</head>
<body>
<%request.setCharacterEncoding("utf-8"); %>
<div class="container">
	
	<div id="header">
	 <div id="login">
    	<%
    		String userID = (String)session.getAttribute("userID");
    		String userName = (String)session.getAttribute("userName");
    		if(userID == null){
    	%>
            <form>
		    	<label>?????????</label>
		    	<input type="text" name="userID" id="loginid" >
		    	<label>??????</label>
		    	<input type="password" name="userPassword" id="loginpw" >
		        <button type="submit">?????????</button>
	        </form>
        <%
    		} else {
        %>
    		<%=session.getAttribute("userID") %>???      <a href="<%=root%>/user/logout.do">????????????</a>
    	<% }%>
        </div>
		<h1>???????????? ?????????</h1>
        <nav>
	        <ul>
	            <li><a href="<%=root%>/">???</a></li>
	            <li><a href="<%=root%>/intro.do">?????????</a></li>
	            <li><a href="<%=root%>/list.jsp">?????????</a></li>
	            <li><a href="<%=root%>/user/detail.do">??? ??????</a></li>
	        </ul>
        </nav>
    </div>
    <h2>?????? ?????????</h2>
    <p id="upbit"></p>
	<div id="content">
		<form action="" method="post">
            <div id="user">
                <label for="">?????????</label>             
            <%
    			if(userID != null){
            %>
            	<%= session.getAttribute("userID") %>??? 
            <%} %>
            </div>
            <% request.setCharacterEncoding("utf-8"); %>
			<div id="title">
				<label for="title">??????</label>
				<input type="text" id="bbsTitle" name="bbsTitle" placeholder="????????? ???????????????"/>
			</div>
			<div id="msg" >
				<label for="content">??????</label>
                <textarea name="bbsContent" id="bbsContent" cols="30" rows="10" placeholder="????????? ???????????????"></textarea>
			</div>
			<div id="btn">
				<button type="submit">??????</button>
				<button type="reset">??????</button>
				<button type="button">??????</button>
			</div>
		</form>
	</div>
</div>
</body>
</html>