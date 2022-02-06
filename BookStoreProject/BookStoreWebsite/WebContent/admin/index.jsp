<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evergreen Bookstore Administration</title>
<link rel="stylesheet" href="../css/style.css" />
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Statistics:</h2>
		<b>
			Total Admins: ${totalUsers} &nbsp;&nbsp;&nbsp;&nbsp;
			Total Books: ${totalBooks} &nbsp;&nbsp;&nbsp;&nbsp;
			Total Customers: ${totalCustomers} &nbsp;&nbsp;&nbsp;&nbsp;
			Total Reviews: ${totalReviews} &nbsp;&nbsp;&nbsp;&nbsp;
			Total Orders: ${totalOrders}
		</b>
	</div>

	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Quick Actions:</h2>
		<b> <a href="new_book">New Book</a> &nbsp; <a href="user_form.jsp">New
				User</a> &nbsp; <a href="category_form.jsp">New Category</a> &nbsp; <a
			href="customer_form.jsp">New Customer</a> &nbsp;
		</b>
	</div>

	<div align="center">
		<hr width="60%" />
		<h2 class="pageheading">Recent Sales:</h2>
		<table border="1" cellpadding="5">
			<tr>
				<th>Order ID</th>
				<th>Ordered By</th>
				<th>Quantity</th>
				<th>Total</th>
				<th>Payment Method</th>
				<th>Status</th>
				<th>Order Date</th>
			</tr>
			<c:forEach items="${listMostRecentSales}" var="order"
				varStatus="status">
				<tr>
					<td><a href="view_order?id=${order.orderId}">${order.orderId}</a></td>
					<td>${order.customer.fullname}</td>
					<td>${order.bookCopies}</td>
					<td><fmt:formatNumber value="${order.total}" type="currency" /></td>
					<td>${order.paymentMethod}</td>
					<td>${order.status}</td>
					<td><fmt:formatDate type='both' value='${order.orderDate}' /></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div align="center">
		<h2 class="pageheading">Recent Reviews:</h2>
		<table border="1" cellpadding="5">
			<tr>
				<th>ID</th>
				<th>Book</th>
				<th>Rating</th>
				<th>Headline</th>
				<th>Customer</th>
				<th>Reviewed On</th>
			</tr>
			<c:forEach items="${listMostRecentReviews}" var="review">
				<tr>
					<td><a href="edit_review?id=${review.reviewId}">${review.reviewId}</a></td>
					<td>${review.book.title}</td>
					<td>${review.rating}</td>
					<td>${review.headline}</td>
					<td>${review.customer.fullname}</td>
					<td><fmt:formatDate type='both' value='${review.reviewTime}' /></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>