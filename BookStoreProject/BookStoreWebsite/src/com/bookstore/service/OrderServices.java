package com.bookstore.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.controller.frontend.shoppingcart.ShoppingCart;
import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;

public class OrderServices {
	private OrderDAO orderDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.orderDAO = new OrderDAO();
	}

	public void listAllOrder(String message) throws ServletException, IOException {
		List<BookOrder> listOrder = orderDAO.listAll();

		request.setAttribute("listOrder", listOrder);

		CommonUtility.forwardToPage(request, response, "order_list.jsp", message);
	}
	
	public void listAllOrder() throws ServletException, IOException {
		listAllOrder(null);
	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));

		BookOrder order = orderDAO.get(orderId);

		if (order == null) {
			String message = "Cannot find order for Order Id: " + orderId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		request.setAttribute("order", order);

		CommonUtility.forwardToPage(request, response, "order_detail.jsp");
	}

	public void showCheckoutForm() throws ServletException, IOException {
		CommonUtility.forwardToPage(request, response, "frontend/checkout.jsp");
	}

	public void placeOrder() throws ServletException, IOException {
		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		String paymentMethod = request.getParameter("paymentMethod");

		String shippingAddress = address + ", " + city + ", " + zipcode + ", " + country;

		BookOrder order = new BookOrder();
		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		order.setCustomer(customer);

		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		Map<Book, Integer> items = shoppingCart.getItems();

		Iterator<Book> iterator = items.keySet().iterator();

		Set<OrderDetail> orderDetails = new HashSet<>();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			Integer quantity = items.get(book);
			float subtotal = quantity * book.getPrice();

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setBook(book);
			orderDetail.setBookOrder(order);
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);

			orderDetails.add(orderDetail);
		}

		order.setOrderDetails(orderDetails);
		order.setTotal(shoppingCart.getTotalAmount());

		orderDAO.create(order);
		shoppingCart.clear();

		String message = "Thank you. Your order has been placed.";
		CommonUtility.showMessageFrontend(request, response, message);

	}

	public void listOrderByCustomer() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		List<BookOrder> listOrders = orderDAO.listByCustomer(customer.getCustomerId());

		request.setAttribute("listOrders", listOrders);

		CommonUtility.forwardToPage(request, response, "frontend/order_list.jsp");
	}

	public void showOrderDetailForCustomer() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		BookOrder order = orderDAO.get(orderId, customer.getCustomerId());

		if (order == null) {
			String message = "Cannot find order for Order Id: " + orderId;
			CommonUtility.showMessageFrontend(request, response, message);
			return;
		}

		request.setAttribute("order", order);

		CommonUtility.forwardToPage(request, response, "frontend/order_detail.jsp");
	}

	public void showEditOrderForm() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		BookOrder order = orderDAO.get(orderId);
		
		if (order == null) {
			String message = "Cannot find order for Order Id: " + orderId;
			CommonUtility.showMessageFrontend(request, response, message);
			return;
		}
		
		request.getSession().setAttribute("order", order);
		
		CommonUtility.forwardToPage(request, response, "order_form.jsp");
						
	}

	public void updateOrder() throws ServletException, IOException {
		HttpSession session = request.getSession();
		BookOrder order = (BookOrder) session.getAttribute("order");
		
		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String shippingAddress = request.getParameter("shippingAddress");
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");
		
		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);
		order.setStatus(orderStatus);
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		
		float total = 0f;
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Integer bookId = orderDetail.getBook().getBookId();
			Integer newQuantity = Integer.parseInt(request.getParameter("quantityFor" + bookId));
			if (newQuantity == 0) {
				iterator.remove();
				continue;
			}
			orderDetail.setQuantity(newQuantity);
			float subtotal = newQuantity * orderDetail.getBook().getPrice();
			orderDetail.setSubtotal(subtotal);
			
			total += subtotal;
		}
		
		order.setTotal(total);
		
		orderDAO.update(order);
		
		String orderDetailPage = request.getContextPath().concat("/admin/view_order?id=" + order.getOrderId());
		response.sendRedirect(orderDetailPage);
	}

	public void deleteOrder() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		BookOrder order = orderDAO.get(orderId);
		
		if (order == null) {
			String message = "Cannot find order for Order Id: " + orderId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}
		
		orderDAO.delete(orderId);
		
		String message = "The order ID " + orderId + " has been deleted.";
		
		listAllOrder(message);
	}
}
