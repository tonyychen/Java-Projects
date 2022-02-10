<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evergreen Books - Online Bookstore</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="css/style.css" />
</head>
<body class="no-margin">
	<div class="banner">
		<div class="banner__content">
			<div class="banner__text">
				<strong>Reminder: this is a TEST site for Educational
					Purposes only. DO NOT leave any sensitive info.</strong> But feel free to
				play around with it.
			</div>
			<button class="banner__close" type="button">
				<span class="material-icons"> close </span>
			</button>
		</div>
	</div>

	<jsp:directive.include file="header.jsp" />

	<div class="center">
		<div>
			<h2>New Books:</h2>
			<c:forEach items="${listNewBooks}" var="book">
				<jsp:directive.include file="book_group.jsp" />
			</c:forEach>
		</div>
		<div>
			<h2>Best-Selling Books:</h2>
			<c:forEach items="${listBestSellingBooks}" var="book">
				<jsp:directive.include file="book_group.jsp" />
			</c:forEach>
		</div>
		<div>
			<h2>Most-Favored Books:</h2>
			<c:forEach items="${listMostFavoredBooks}" var="book">
				<jsp:directive.include file="book_group.jsp" />
			</c:forEach>
		</div>
		<br /> <br />
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script>
	document.querySelector(".banner__close").addEventListener("click",
			function() {
				this.closest(".banner").style.display = "none";
			})
</script>
</html>