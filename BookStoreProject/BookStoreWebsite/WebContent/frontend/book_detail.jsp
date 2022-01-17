<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${book.title}</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<table width="80%" style="border: 0">
			<tr>
				<td colspan="3" align="left">
					<p id="book-title">${book.title}</p> by <span id="book-author">${book.author}</span>
				</td>
			</tr>
			<tr height="50px">
				<td rowspan="2" width="20%"><img class="book-large"
					src="data:image/jpg;base64,${book.base64Image}" /></td>
				<td valign="top" align="left"><jsp:directive.include
						file="book_rating.jsp" /> 
						&nbsp;&nbsp; 
						<u><a href="#reviews">${fn:length(book.reviews)} Reviews</a></u>
				</td>
				<td valign="top" rowspan="2" width="20%">
					<h2>$${book.price}</h2> <br /> <br />
					<button type="submit">Add to Cart</button>
				</td>
			</tr>
			<tr>
				<td id="description">${book.description}</td>
			</tr>

			<tr>
				<td><h2>
						<a id="reviews">Customer Reviews</a>
					</h2></td>
				<td colspan="2">
					<button>Write a Customer Review</button>
				</td>
			</tr>

			<tr>
				<td colspan="3">
					<table style="border: 0">
						<c:forEach items="${book.reviews}" var="review">
							<tr>
								<td><c:forTokens items="${review.stars}" delims=","
										var="star">
										<c:if test="${star eq 'on'}">
											<img src="images/rating_on.png" />
										</c:if>
										<c:if test="${star eq 'off'}">
											<img src="images/rating_off.png" />
										</c:if>
									</c:forTokens> - <b>${review.headline}</b></td>
								<td rowspan="2" style="width: 259px; vertical-align: top;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>${review.comment}</i></td>
							</tr>
							<tr>
								<td>by ${review.customer.fullname} on <fmt:formatDate
										type='both' value='${review.reviewTime}' />
								</td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>

	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>