<%@page import="com.bit.model.bbs.BbsVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BBS</title>
<%
String root = request.getContextPath();
%>
    <link rel="stylesheet" href="<%=root%>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%=root%>/css/upbit.css">
    <style type="text/css" rel="stylesheet">
        h2{
            text-align: center;
        }
        #upbit{
            margin-left: 150px;
        }
        #add{
            display: block;
            float: right;
            margin-bottom: 20px;
            margin-right: 50px;
            background-color: #036;
            padding: 10px;
            color: #fff;
            border-radius: 10px;
        }
        table{
            border-collapse: collapse;
            width: 1200px;
            text-align: left;
            line-height: 1.5;
        }
        table thead th{
            padding: 10px;
            font-weight: bold;
            vertical-align: top;
            color: #369;
            border-bottom: 3px solid #036;
        }
        table td{
            width: 350px;
            padding: 10px;
            vertical-align: top;
            border-bottom: 1px solid #ccc;
        }
        table tbody th {
            width: 150px;
            padding: 10px;
            font-weight: bold;
            vertical-align: top;
            border-bottom: 1px solid #ccc;
            background: #f3f6f7;
        }
        table th:first-child,
        table th:nth-child(3),
        table th:last-child{
            width: 100px;
        }
        footer{
        	margin-top:60px;
            clear: both;
            background-color: gray;
        }
        #page{
        	overflow: hidden;
        	list-style: none;
    		padding: 5px;
   		 	width:100px;
    		margin: 5px auto;
        }
        .btn-success {
		  color: #fff;
		  background-color: #5cb85c;
		  border-color: #4cae4c;
          
          text-align: center;
          text-decoration: none;
          margin: 0px auto;
          width: 300px;
		}
    </style>
    <script type="text/javascript" src="<%=root%>/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function (){
    $('#upbit').load("<%=root%>/bitslide.html");
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
	$('#login form').submit(function () {
		$(this).find('span').remove();
		$(this).find(":text").each(function(idx,ele){
			if($(ele).val()=='')
				$(ele).after('<span class="err">빈값허용안함</span>');
		});
		login();
		return false;
	});
	getList();
});
var login = function () {
	var param = {
				userID:$('#login input').eq(0).val(),
				userPassword:$('#login input').eq(1).val()
				};
	$.post('http://localhost:8080/bbsproject/user/login', $.param(param), function(data,info){
		location.href='http://localhost:8080/bbsproject/index.jsp';
	});
};


function getList() {
	$.getJSON('http://localhost:8080/bbsproject/api/list',function(data){
		$(data.bbs).each(function(idx,ele){
			$('<tr/>')	
			.append($('<td/>').html('<a href="detail.jsp?bbsID='+ele.bbsID+'">'+ele.bbsID+'</a>'))
            .append($('<td/>').html('<a href="detail.jsp?bbsID='+ele.bbsID+'">'+ele.bbsTitle+'</a>'))
            .append($('<td/>').html('<a href="detail.jsp?bbsID='+ele.bbsID+'">'+ele.userID+'</a>'))
            .append($('<td/>').html('<a href="detail.jsp?bbsID='+ele.bbsID+'">'+ele.bbsDate.substring(5, 11)+ ele.bbsDate.substring(11, 13) + "시"
					+ ele.bbsDate.substring(14, 16) + "분" +'</a>'))
          .appendTo('.tbody');
			
		});
	});
}

</script>
</head>
<body>
	<%
		int pageNumber = 1; //기본은 1 페이지를 할당
		// 만약 파라미터로 넘어온 오브젝트 타입 'pageNumber'가 존재한다면
		// 'int'타입으로 캐스팅을 해주고 그 값을 'pageNumber'변수에 저장한다
		if(request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
	%>
    <div>
    <header>
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
    		<%=session.getAttribute("userID") %>님      <a href="<%=root%>/user/logout">로그아웃</a>
    	<% }%>
        </div>
        <h1>암호화폐 게시판</h1>
    </header>
    <nav>
        <ul>
            <li><a href="<%=root%>/">홈</a></li>
            <li><a href="<%=root%>/intro.do">인트로</a></li>
            <li><a href="<%=root%>/list.jsp">게시판</a></li>
            <li><a href="<%=root%>/user/detail.do">내 정보</a></li>
        </ul>
    </nav>
        <h2>게시판</h2>
        <form action="add.jsp">
        <div id="upbit"></div>
        <button id="add">글쓰기</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>글 번호</th>
                    <th>글 제목</th>
                    <th>작성자</th>
                    <th>날짜</th>
                </tr>
            </thead>
            <tbody class = tbody>
            <%request.setCharacterEncoding("utf-8"); %>
				
            </tbody>
        </table>
        
        	<!-- 페이징 처리 영역 -->
			<div id="page">
				<a href="list.do?pageNumber=<%=pageNumber - 1 %>"
					class="btn btn-success btn-arraw-left">이전</a>

				<a href="list.do?pageNumber=<%=pageNumber + 1 %>"
					class="btn btn-success btn-arraw-left">다음</a>
			</div>
    </div>
</body>
</html>