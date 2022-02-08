<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body class="no-margin">
	<div class="banner">
		<div class="banner__content">
			<div class="banner__text">
				<strong>Reminder: this is a TEST site.</strong> Feel free to play
				around with it. <br /> Sample Admin Login - <Strong>
					Email: admin@gmail.com &nbsp;&nbsp;&nbsp; Password: password</Strong>
			</div>
			<button class="banner__close" type="button">
				<span class="material-icons"> close </span>
			</button>
		</div>
	</div>

	<div align="center">
		<h1>Bookstore Administration</h1>
		<h2>Admin Login</h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>

		<form id="loginForm" action="login" method="post">
			<table>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" id="email" size="20"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password"
						size="20" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#loginForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				password : "required"
			},

			messages : {
				email : {
					required : "Please enter email",
					email : "Please enter an valid email address"
				},
				password : "Please enter password"
			}
		});
	});

	document.querySelector(".banner__close").addEventListener("click",
			function() {
				this.closest(".banner").style.display = "none";
			})
</script>
</html>