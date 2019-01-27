<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up page</title>
</head>
<body>
<a href = "http://localhost:8080/WebApp/DBActionsServlet">All users</a><br>
<a href = 'http://localhost:8080/WebApp'>To main</a><br>
<form action = "SignUpServlet" method = "POST">
	<h3>Registration of the new user</h3>
	<table>
		<tr>
			<td>Login : </td>
			<td><input type = "text" name = "login" value = "<c:out value = "${user['login']}"/>"> *</td>
			<td><c:out value = "${errors['login']}"/></td>
		</tr>
		<tr>
			<td>First name : </td>
			<td><input type = "text" name = "fname" value = "<c:out value = "${user['fname']}"/>"></td>
			<td><c:out value = "${errors['fname']}"/></td>
		</tr>
		<tr>
			<td>Middle name : </td>
			<td><input type = "text" name = "mname" value = "<c:out value = "${user['mname']}"/>"></td>
			<td><c:out value = "${errors['mname']}"/></td>
		</tr>
		<tr>
			<td>Surname : </td>
			<td><input type = "text" name = "sname" value = "<c:out value = "${user['sname']}"/>"></td>
			<td><c:out value = "${errors['sname']}"/></td>
		</tr>
		<tr>
			<td>Email : </td>
			<td><input type = "text" name = "email" value = "<c:out value = "${user['email']}"/>"> *</td>
			<td><c:out value = "${errors['email']}"/></td>
		</tr>
		<tr>
			<td>Password : </td>
			<td><input type = "password" name = "pass"> *</td>
			<td><c:out value = "${errors['pass']}"/></td>
		</tr>
		<tr>
			<td>Repeat password : </td>
			<td><input type = "password" name = "pass2"> *</td>
		</tr>
		<tr>
			<td>Role : </td>
			<td>
				<select name = "role" size = "2">
					<option selected value = "Student">Student</option>
					<option value = "Teacher">Teacher</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><input type = "submit" name = "signup" value = "Go"></td>
			<td><input type = "reset" name = "resetbutton" value = "Reset"></td>
		</tr>
	</table>
</form>
</body>
</html>