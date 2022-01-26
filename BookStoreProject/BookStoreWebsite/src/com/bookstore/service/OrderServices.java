package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.BookOrder;

public class OrderServices {
	private OrderDAO orderDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.orderDAO = new OrderDAO();
	}

	public void listAllOrder() throws ServletException, IOException {
		List<BookOrder> listOrder = orderDAO.listAll();
		
		request.setAttribute("listOrder", listOrder);
		
		CommonUtility.forwardToPage(request, response, "order_list.jsp");
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
}
