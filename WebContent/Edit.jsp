<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="javascript:history.back()">
<img  alt="cancel" src="img/cancel.png" style="width:20px; height:20px"></a>
<form action = "Update" method = "POST">
	<h3>Edit information about user <c:out value = "${userdata['login']}"/> </h3>
	<table>
		<tr>
			<td>First name : </td>
			<td><input type = "text" name = "fname" value = "<c:out value = "${userdata['fname']}"/>"></td>
			<td><c:out value = "${errors['fname']}"/></td>
		</tr>
		<tr>
			<td>Middle name : </td>
			<td><input type = "text" name = "mname" value = "<c:out value = "${userdata['mname']}"/>"></td>
			<td><c:out value = "${errors['mname']}"/></td>
		</tr>
		<tr>
			<td>Surname : </td>
			<td><input type = "text" name = "sname" value = "<c:out value = "${userdata['sname']}"/>"></td>
			<td><c:out value = "${errors['sname']}"/></td>
		</tr>
		<tr>
			<td>Email : </td>
			<td><input type = "text" name = "email" value = "<c:out value = "${userdata['email']}"/>"> *</td>
			<td><c:out value = "${errors['email']}"/></td>
		</tr>
		<tr>
			<td>Password : </td>
			<td><input type = "password" name = "pass" value = "<c:out value = "${userdata['pass']}"/>"> *</td>
			<td><c:out value = "${errors['pass']}"/></td>
		</tr>
		<tr>
			<td>Repeat password : </td>
			<td><input type = "password" name = "pass2"> *</td>
		</tr>
		<tr>
			<td><input type = "hidden" name = "login" value = "${userdata['login']}">
			<input type = "hidden" name = "userid" value = "${userid}">
			<input type = "submit" name = "edit" value = "Go"></td>
		</tr>
	</table>
</form>
</body>
</html>