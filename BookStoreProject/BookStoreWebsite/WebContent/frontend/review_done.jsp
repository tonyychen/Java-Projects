<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Posted - Online Bookstore</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Review from ${loggedCustomer.fullname}</h2>
		<h3 class="message">Your review has been submitted. Thank you!</h3>
		<button onclick="window.history.go(-2); return false;">Go Back</button>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

</html>