<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Order - Evergreen Bookstore Administration</title>
<link rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Edit Order ID: ${order.orderId}</h2>
	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>

	<form action="update_order" method="post" id="orderForm">
		<div align="center">
			<table border="1" cellpadding="5">
				<tr>
					<td><b>Ordered By: </b></td>
					<td>${order.customer.fullname}</td>
				</tr>
				<tr>
					<td><b>Order Date: </b></td>
					<td><fmt:formatDate type='both' value='${order.orderDate}' /></td>
				</tr>
				<tr>
					<td><b>Recipient Name: </b></td>
					<td><input type="text" name="recipientName"
						value="${order.recipientName}" size="45" /></td>
				</tr>
				<tr>
					<td><b>Recipient Phone: </b></td>
					<td><input type="text" name="recipientPhone"
						value="${order.recipientPhone}" size="45" /></td>
				</tr>
				<tr>
					<td><b>Ship to: </b></td>
					<td><input type="text" name="shippingAddress"
						value="${order.shippingAddress}" size="45" /></td>
				</tr>
				<tr>
					<td><b>Payment Method: </b></td>
					<td><select name="paymentMethod">
							<option value="Cash On Delivery"
								${order.paymentMethod == 'Cash On Delivery' ? "selected" : ''}>Cash
								On Delivery</option>
					</select></td>
				</tr>
				<tr>
					<td><b>Order Status: </b></td>
					<td><select name="orderStatus">
							<option value="Processing"
								${order.status == 'Processing' ? "selected" : ''}>Processing</option>
							<option value="Shipped"
								${order.status == 'Shipped' ? "selected" : ''}>Shipped</option>
							<option value="Delivered"
								${order.status == 'Delivered' ? "selected" : ''}>Delivered</option>
							<option value="Completed"
								${order.status == 'Completed' ? "selected" : ''}>Completed</option>
							<option value="Cancelled"
								${order.status == 'Cancelled' ? "selected" : ''}>Cancelled</option>
					</select></td>
				</tr>
			</table>
		</div>
		<br /> <br />
		<div align="center">
			<h2>Ordered Books:</h2>
			<table border="1" cellpadding="5">
				<tr>
					<th>Index</th>
					<th colspan="2">Book Title</th>
					<th>Author</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Subtotal</th>
					<th></th>
				</tr>
				<c:forEach items="${order.orderDetails}" var="orderDetail"
					varStatus="status">
					<tr id="detailFor${orderDetail.book.bookId}">
						<td>${status.index + 1}</td>
						<td><img class="book-mini"
							src="data:image/jpg;base64,${orderDetail.book.base64Image}" /></td>
						<td>${orderDetail.book.title}</td>
						<td>${orderDetail.book.author}</td>
						<td><fmt:formatNumber value="${orderDetail.book.price}"
								type="currency" /></td>
						<td><input type="text"
							name="quantityFor${orderDetail.book.bookId}"
							value="${orderDetail.quantity}" size="5"
							id="quantityFor${orderDetail.book.bookId}" /></td>
						<td><fmt:formatNumber value="${orderDetail.subtotal}"
								type="currency" /></td>
						<td><a href=""
							onclick="remove('${orderDetail.book.bookId}'); return false;">Remove</a></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5" align="right"><b>Total:</b></td>
					<td><b>${order.bookCopies}</b></td>
					<td colspan="2"><b><fmt:formatNumber
								value="${order.total}" type="currency" /></b></td>
				</tr>
			</table>
		</div>
		<br /> <br />
		<div align="center">
			<button onclick="DeleteOrder(); return false;" style="color: red">Delete
				this Order</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="submit">Update/Recalculate</button>
			<br /> <br />
			<button onclick="GoToListOrderPage(); return false;">Cancel</button>
		</div>
	</form>

	<jsp:directive.include file="footer.jsp" />

	<script>
		$(document).ready(function() {
			$("#orderForm").validate({
				rules: {
					recipientName: "required",
					recipientPhone: "required",
					shippingAddress: "required",
					paymentMethod: "required",
					orderStatus: "required",
					<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
						quantityFor${orderDetail.book.bookId}: {
							required: true, 
							number: true,
							min: 0},
					</c:forEach>
				},
				
				messages: {
					recipientName: "Please enter recipient name",
					recipientPhone: "Please enter recipient phone",
					shippingAddress: "Please enter shipping address",
					paymentMethod: "Please select payment method",
					orderStatus: "Please select order status",
					<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
						quantityFor${orderDetail.book.bookId}: {
							required: "Please enter quantity",
							number: "Quantity must be a number",
							min: "Quantity must be >= 0"},
					</c:forEach>
				}
			});
		});
		
		function DeleteOrder() {
			if (confirm('Are you sure want to delete the order with ID ${order.orderId}?')) {
				window.location = 'delete_order?id=${order.orderId}';
			}
		}
		
	
	    function remove(id) {
	        var row = document.getElementById("detailFor" + id);
	        row.style.display = 'none';
	        
	        document.getElementById("quantityFor" + id).value = 0;
	    }
		
		function GoToListOrderPage() {
			window.location.href = "list_order";
		}
	</script>
</body>
</html>