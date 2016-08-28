<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script>
	var baseURL = "http://hsh688.cafe24.com/";
		function signup(){
			$.ajax({
				url : "${pageContext.request.contextPath}/member_add",
				//url : baseURL + "member_add",
				type : 'post',
				data : {
					userId : $("#userId").val(),
					userPw : $("#password").val()
				},
				success : function(data){
					alert(data);
				},
				error : function(error){
					alert(error);	
				}
			})
		}
		function login(){
			$.ajax({
				url : "${pageContext.request.contextPath}/j_spring_security_check",
				//url : baseURL + "j_spring_security_check",
				type : 'post',
				data : {
					j_username : $("#userId_login").val(),
					j_password : $("#password_login").val()
				},
				success : function($data){
					alert(JSON.stringify($data));
				}
			})
		}
	</script>
</head>
<body>
<h1>
	Hello world!  
</h1>
	<input type="password" id="password"/>pw
	</br>
	<input type="text" id="userId"/>userId
	<button onclick="signup()">회원가입</button>
	
	</br>
	
	id<input type="text" id="userId_login"/>
	</br>
	pw<input type="password" id="password_login"/>
	</br>
	<button onclick="login()">로그인</button>
	</br>
	<br>
	<form action = "${pageContext.request.contextPath}/shit/shit_add" method="post">
		<input type="text" name="userKey" value="2"/>
		<input type="text" name="shitNm" value="모닝똥"/>
		<input type="text" name="shitType" value="A"/>
		<input type="text" name="shitThick" value="A"/>
		<input type="text" name="shitColor" value="A"/>
		<input type="text" name="shitAmount" value="A"/>
		<buttom onclick="$('form').submit()">입력</buttom>
	</form>
</body>
</html>
