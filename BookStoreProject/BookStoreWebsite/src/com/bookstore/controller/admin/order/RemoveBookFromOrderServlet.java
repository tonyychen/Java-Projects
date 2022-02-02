package com.bookstore.controller.admin.order;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.entity.BookOrder;
import com.bookstore.entity.OrderDetail;
import com.bookstore.service.CommonUtility;

//Note! This servlet is orphan for now as the remove rule in admin/order_form.jsp has been changed
@WebServlet("/admin/remove_book_from_order")
public class RemoveBookFromOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		BookOrder order = (BookOrder) session.getAttribute("order");
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		
		//remove orderDetail
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if (orderDetail.getBook().getBookId() == bookId) {
				iterator.remove();
			}
		}
		
		//update total
		float newTotal = 0f;
		for (OrderDetail detail : orderDetails) {
			newTotal += detail.getSubtotal();
		}
		order.setTotal(newTotal);
		
		
		CommonUtility.forwardToPage(request, response, "order_form.jsp");
	}

}
