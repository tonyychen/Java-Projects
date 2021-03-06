<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Info - Evergreen Bookstore</title>
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>You already wrote a review for this book</h2>
			<table class="form" width="60%">
				<tr>
					<td><span id="book-title">${book.title}</span></td>
					<td>
						<b>Rating:</b>
						<div id="rateYo">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<img class="book-large" src="data:image/jpg;base64,${book.base64Image}" />
					</td>
					<td valign="top">
						Headline: <br/>
						<input type="text" name="headline" size="60" readonly="readonly" value="${review.headline}" /> 
						<br />
						<br />
						Details: <br/>
						<textarea name="comment" cols="70" rows="15" readonly="readonly">${review.comment}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="100%" align="center">
						<button onclick="goToBookDetails(); return false;">Go Back</button>
					</td>
				</tr>
			</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#rateYo").rateYo({
			rating : ${review.rating},
			readOnly: true
		});
	});
	
	function goToBookDetails() {
		window.location = 'view_book?id=' + ${book.bookId};
	}
</script>
</html>