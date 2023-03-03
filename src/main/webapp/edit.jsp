<%@page import="com.bit.model.bbs.BbsVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>edit</title>
<%
String root = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=root%>/css/header.css">
<style type="text/css">
h2{
	text-align: center;
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
    font-weight: bold;
}
#content #btn button a{
	color: white;
	font-weight: bold;
	text-decoration: none;
}
#footer{
	margin-top: 60px;
	clear: both;
	background-color: gray;
}
</style>
<script type="text/javascript" src="<%=root%>/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
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
		updateOne(e.target);
	});
		var bbsID=location.search.substr(location.search.indexOf('=')+1);
		getOne(bbsID);
});
function getOne(bbsID){
	$.ajax({
		  url: 'http://192.168.99.100:8080/bbsproject/api/list/' + bbsID,
		  type: 'get',
		  contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		  data: {bbsID:bbsID},
		  dataType: "json",
		  success: function(data){ 
                $('#bbstitle').html('<input type="text" id="bbsTitle" name="bbsTitle" value="'+data['bbs']['bbsTitle']+'"/>');
				$('#bbsContent').html('<textarea name="bbsContent" cols="30" rows="10" id="bbsContent">'+data['bbs']['bbsContent']+'</textarea>');
				$('#bbsID').html(data['bbs']['bbsID'] +"번 게시글");
		  }
    });
}
function updateOne(form){
	var bbsID=location.search.substr(location.search.indexOf('=')+1);
	var param=$(form).serialize();
    $.post('http://192.168.99.100:8080/bbsproject/api/list/'+bbsID , param,function(data){
        location.href='http://localhost:8080/bbsproject/detail.jsp?bbsID='+bbsID;
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
		    	<label>아이디</label>
		    	<input type="text" name="userID" id="loginid" >
		    	<label>비번</label>
		    	<input type="password" name="userPassword" id="loginpw" >
		        <button type="submit">로그인</button>
	        </form>
        <%
    		} else {
        %>
    		<%=session.getAttribute("userID") %>님      <a href="<%=root%>/user/logout.do">로그아웃</a>
    	<% }%>
        </div>
		<h1>암호화폐 게시판</h1>
        <nav>
	        <ul>
	            <li><a href="<%=root%>/">홈</a></li>
	            <li><a href="<%=root%>/intro.do">인트로</a></li>
	            <li><a href="<%=root%>/list.jsp">게시판</a></li>
	            <li><a href="<%=root%>/user/detail.do">내 정보</a></li>
	        </ul>
        </nav>
    </div>
    <h2 id="bbsID"></h2>
	<div id="content">
		<form>
            <div id="user">
                <label for="">작성자</label>             
            	<span id="userID"><%= session.getAttribute("userID") %>님</span> 
            </div>
			<div id="title">
				<label for="title">제목</label>
				<span id="bbstitle"></span>
			</div>
			<div id="msg" >
				<label for="content">내용</label>
                <span id="bbsContent"></span>
			</div>
			<div id="btn">
				<button>수정</button>
				<button type="reset">취소</button>
				<button type="button">뒤로</button>
			</div>
		</form>
	</div>
</div>
</body>
</html>
</body>
</html>