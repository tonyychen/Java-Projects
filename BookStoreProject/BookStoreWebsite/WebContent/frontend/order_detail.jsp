<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Order Details - Evergreen Bookstore</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Your Order ID: ${order.orderId}</h2>
	</div>

	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<td><b>Order Status: </b></td>
				<td>${order.status}</td>
			</tr>
			<tr>
				<td><b>Order Date: </b></td>
				<td><fmt:formatDate type='both' value='${order.orderDate}' /></td>
			</tr>
			<tr>
				<td><b>Quantity: </b></td>
				<td>${order.bookCopies}</td>
			</tr>
			<tr>
				<td><b>Total Amount: </b></td>
				<td><fmt:formatNumber value="${order.total}" type="currency" /></td>
			</tr>
			<tr>
				<td><b>Recipient Name: </b></td>
				<td>${order.recipientName}</td>
			</tr>
			<tr>
				<td><b>Recipient Phone: </b></td>
				<td>${order.recipientPhone}</td>
			</tr>
			<tr>
				<td><b>Ship to: </b></td>
				<td>${order.shippingAddress}</td>
			</tr>
			<tr>
				<td><b>Payment Method: </b></td>
				<td>${order.paymentMethod}</td>
			</tr>
		</table>
	</div>
	<br />
	<br />
	<div align="center">
		<h2>Ordered Books:</h2>
		<table border="1" cellpadding="5">
			<tr>
				<th></th>
				<th colspan="2">Book</th>
				<th>Author</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Subtotal</th>
			</tr>
			<c:forEach items="${order.orderDetails}" var="orderDetail"
				varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td><img class="book-mini"
						src="data:image/jpg;base64,${orderDetail.book.base64Image}" /></td>
					<td>${orderDetail.book.title}</td>
					<td>${orderDetail.book.author}</td>
					<td><fmt:formatNumber value="${orderDetail.book.price}"
							type="currency" /></td>
					<td>${orderDetail.quantity}</td>
					<td><fmt:formatNumber value="${orderDetail.subtotal}"
							type="currency" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right"><b>Total:</b></td>
				<td><b>${order.bookCopies}</b></td>
				<td><b><fmt:formatNumber value="${order.total}"
							type="currency" /></b></td>
			</tr>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>