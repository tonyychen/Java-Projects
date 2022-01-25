<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evergreen Books - Online Bookstore</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div class="center">
		<div>
			<h2>New Books:</h2>
			<c:forEach items="${listNewBooks}" var="book">
				<div class="book">
					<div>
						<a href="view_book?id=${book.bookId}"> <img class="book-small"
							src="data:image/jpg;base64,${book.base64Image}" />
						</a>
					</div>
					<div>
						<a href="view_book?id=${book.bookId}"> <b>${book.title}</b>
						</a>
					</div>
					<div>
						<jsp:directive.include file="book_rating.jsp" />
					</div>
					<div>
						<i>by ${book.author}</i>
					</div>
					<div>
						<b><fmt:formatNumber value="${book.price}" type="currency" /></b>
					</div>
				</div>
			</c:forEach>
		</div>
		<div>
			<h2>Best-Selling Books:</h2>
		</div>
		<div>
			<h2>Most-Favored Books:</h2>
		</div>
		<br /> <br />
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>