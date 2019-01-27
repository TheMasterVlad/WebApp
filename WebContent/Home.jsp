<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="http://icons.iconarchive.com/icons/papirus-team/papirus-apps/256/java-icon.png">
<meta charset="UTF-8">
<title>My website</title>
</head>
<body>
<c:choose>
	<c:when test = "${empty login}">
		<h1>Hello, fella! Please log in or sign up.</h1>
		<form action = "LogIn" method = "POST">
			<table>
				<tr>
					<td><img  alt="signup" src="img/signup.png" style="width:20px; height:20px">
					<a href = 'http://localhost:8080/WebApp/SignUp.jsp'>Sign up</a><br></td>
					<td><img  alt="all users" src="img/getall.png" style="width:20px; height:20px">
					<a href = "http://localhost:8080/WebApp/DBActionsServlet">All users</a></td>
				</tr>
				<tr><td></td></tr>
				<tr>
					<td>login or email: </td>
					<td><input type = "text" name = "loginoremail"></td>
					<td><c:out value = "${errors['loginoremail']}"/></td>
				</tr>
				<tr>
					<td>password: </td>
					<td><input type = "password" name = "pass"></td>
					<td><c:out value = "${errors['pass']}"/></td>
				</tr>
				<tr>
					<td align = "right" colspan = "2"><input type = "submit" value = "Log In"></td>
				</tr>
			</table>
		</form>
	</c:when>	
	<c:otherwise>
		<h1>Hello, <c:out value = "${login}"/> ! You've successfully logged in.</h1>
		<img  alt="logout" src="img/logout.png" style="width:20px; height:20px">
		<a href = 'LogIn?action=logout'>Log out</a><br>
	</c:otherwise>
</c:choose>
	<form action = "DBActionsServlet" method = "POST">
		<input type = "submit" value = "Show all users" name = "showusers">
	</form>
</body>
</html>