<%@page import="com.bit.model.bbs.BbsVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>detail</title>
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
#content span{
	display: inline-block;
	width: 100px;
	height: 35px;
	line-height: 40px;
}
#content #user span{
    width: 25%;
    margin-right: 50px;
}
#content #title span{
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
table {
  border-spacing: 0;
  border-collapse: collapse;
  background-color: transparent;
  width: 800px;
  height: 300px;
  margin: 0px auto;
}
thead {
    display: table-header-group;
}
td,
th {
  padding: 0;
  text-align: left;
}
.row {
	margin: 0px auto;
}
.row:before,
.row:after{
  display: table;
  content: " ";
  clear: both;
}
.table-striped > tbody > tr:nth-of-type(odd) {
  background-color: #f9f9f9;
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
		var bbsID=location.search.substr(location.search.indexOf('=')+1);
		deleteOne(bbsID);
	});
	
	var bbsID=location.search.substr(location.search.indexOf('=')+1);
	getOne(bbsID);
});

function getOne(bbsID){
	$.ajax({
		  url: '192.168.99.100:8888/bbsproject/api/list/' + bbsID,
		  type: 'get',
		  contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		  data: {bbsID:bbsID},
		  dataType: "json",
		  success: function(data){
			  
				$('<tr/>')	
				.append($("<td/>").html("?????????"))
                .append($('<td/>').html(data['bbs']['bbsTitle']))
                .appendTo('.tbody');
                $('<tr/>')
                .append($("<td/>").html("?????????"))
                .append($('<td/>').html( data['bbs']['userID']))
                .appendTo('.tbody');
                $('<tr/>')
                .append($("<td/>").html("????????????"))
                .append($('<td/>').html( data['bbs']['bbsDate'].substring(5, 11)+ data['bbs']['bbsDate'].substring(11, 13) + "???"
						+ data['bbs']['bbsDate'].substring(14, 16) + "???"))
				.appendTo('.tbody');
				$('<tr/>')	
				.append($("<td/>").html("??????"))
				.append($('<td/>').html(data['bbs']['bbsContent']))		
                .appendTo('.tbody');
				var bbsID = data['bbs']['bbsID'];
				$('#bbsID').html(data['bbs']['bbsID'] +"??? ?????????");
				$('#edit').html('<a href="edit.jsp?bbsID='+bbsID+'">??????</a>');			
				$('#delete').html('<a >??????</a>');
				
		  }
    });
	
}
function deleteOne(bbsID){
	$.ajax({
			 url: 'http://192.168.99.100:8888/bbsproject/api/list/'+ bbsID,
			 type: 'delete',
			 success: function(){
				location.href='http://192.168.99.100:8080/bbsproject/index.jsp';
			 },
			 error: function(xhr,status,err){
				  alert("??????("+status+")");
				  console.log(status);
				  console.log('---------------');
				  console.log(err);
			  }
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
    <h2 id="bbsID"></h2>
	<div id="content">
		<div class="row">
		<form>
				<table class="table table-striped" style="text-align:enter; border : 1px solid #dddddd;">
					<thead>
						<tr>
							<th colspan="3" style="background-color: #eeeeee; text-align:center;">????????? ??? ??????</th>
						</tr>
					</thead>
					<tbody class = tbody>
					
					<!-- ????????? ????????? ?????? -->
					</tbody>
				</table>
			
				<div id="btn">
					<button id="edit"></button>
					<button type="submit" id="delete"></button>
					<button type="button">??????</button>
				</div>
			</form>

	</div>
	<div id="footer">
	    ????????? footer?????????. 
	</div>
</div>
</div>
</body>
</html>
</body>
</html>