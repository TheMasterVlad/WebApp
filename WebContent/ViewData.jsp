<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All users</title>
</head>
<body>
<a href = 'http://localhost:8080/WebApp'><img  alt="home" src="img/home.png" title = "To main" style="width:30px; height:30px"></a>
<a href = 'http://localhost:8080/WebApp/SignUp.jsp'><img  alt="signup" src="img/add.png" title = "Add new user" style="width:30px; height:30px"></a><br>
<table border="1">
	<tr>
		<th>Login</th>
		<th>First name</th>
		<th>Middle name</th>
		<th>Surname</th>
		<th>Email</th>
		<th>Registration data</th>
		<th>Role</th>
	</tr>
	<c:forEach var="entry" items ="${users}">
		<tr>
			<c:forEach items="${entry.value}" var="item">
				<td>${item}</td>
			</c:forEach>
			<td><a href="http://localhost:8080/WebApp/DBActionsServlet?deleteuserid=${entry.key}">
			<img  alt="delete" title = "Delete user" src="${pageContext.servletContext.contextPath}/img/trash.png" style="width:20px; height:20px"></a></td>
			<td><a href="http://localhost:8080/WebApp/DBActionsServlet?edituserid=${entry.key}">
			<img  alt="edit" title = "Edit user" src="${pageContext.servletContext.contextPath}/img/edit.png" style="width:20px; height:20px"></a></td>
		</tr>
	</c:forEach>
</table>
<br><c:out value = "${ok}"/>
</body>
</html>