<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout - Evergreen Bookstore</title>
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>

		<c:set var="cart" value="${sessionScope['cart']}" />

		<c:if test="${cart.totalItems == 0}">
			<h2>There are no items in your cart</h2>
		</c:if>

		<c:if test="${cart.totalItems > 0}">
			<div>
				<h2>Review Your Order Details</h2>
				<table border="1">
					<tr>
						<th>No</th>
						<th colspan="2">Book</th>
						<th>Author</th>
						<th>Price</th>
						<th>Quantity</th>
						<th>Subtotal</th>
					</tr>
					<c:forEach items="${cart.items}" var="item" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><img class="book-mini"
								src="data:image/jpg;base64,${item.key.base64Image}" /></td>
							<td>${item.key.title}</td>
							<td>${item.key.author}</td>
							<td><fmt:formatNumber value="${item.key.price}"
									type="currency" /></td>
							<td>${item.value}</td>
							<td><fmt:formatNumber value="${item.value * item.key.price}"
									type="currency" /></td>
						</tr>
					</c:forEach>

					<tr>
						<td colspan="3"></td>
						<td><b>${cart.totalQuantity} book(s)</b></td>
						<td><b>Total:</b></td>
						<td colspan="2"><b><fmt:formatNumber
									value="${cart.totalAmount}" type="currency" /></b></td>
					</tr>
				</table>
			</div>
			<div>
				<h2>Your Shipping Information</h2>
				<form id="orderForm" action="place_order" method="post">
					<table style="border: 0">
						<tr>
							<td>Recipient Name:</td>
							<td><input type="text" name="recipientName"
								value="${loggedCustomer.fullname}" /></td>
						</tr>
						<tr>
							<td>Recipient Phone:</td>
							<td><input type="text" name="recipientPhone"
								value="${loggedCustomer.phone}" /></td>
						</tr>
						<tr>
							<td>Street Address:</td>
							<td><input type="text" name="address"
								value="${loggedCustomer.address}" /></td>
						</tr>
						<tr>
							<td>City:</td>
							<td><input type="text" name="city"
								value="${loggedCustomer.city}" /></td>
						</tr>
						<tr>
							<td>Zip Code:</td>
							<td><input type="text" name="zipcode"
								value="${loggedCustomer.zipcode}" /></td>
						</tr>
						<tr>
							<td>Country:</td>
							<td><input type="text" name="country"
								value="${loggedCustomer.country}" /></td>
						</tr>
					</table>
					<br />
					<div>
						<h2>Payment</h2>
						Choose your payment method: &nbsp;&nbsp;&nbsp; <select
							name="paymentMethod">
							<option value="Cash On Delivery">Cash On Delivery</option>
						</select>
					</div>
					<br /> <br />
					<div>
						<button onclick="goToHomePage(); return false;">Continue
							Shopping</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="submit" style="background-color: orange">
							<b>Place Order</b>
						</button>
					</div>
				</form>
			</div>
		</c:if>


	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#orderForm').validate({
			rules: {
				recipientName: "required",
				recipientPhone: "required",
				address: "required",
				city: "required",
				zipcode: "required",
				country: "required",
			},
			
			messages: {
				recipientName: "Please enter recipient name",
				recipientPhone: "Please enter phone number",
				address: "Please enter street address",
				city: "Please enter city",
				zipcode: "Please enter zip code",
				country: "Please enter country",
			},
		
		
		});
	});

	function goToHomePage() {
		window.location.href = "${pageContext.request.contextPath}";
	}
</script>
</html>