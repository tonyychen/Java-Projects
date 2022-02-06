package com.bookstore.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CustomerDAO;
import com.bookstore.dao.OrderDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstore.dao.UserDAO;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Review;

@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminHomeServlet() {
        super();
    }
    
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO();
		List<BookOrder> listMostRecentSales = orderDAO.listMostRecentSales();
		request.setAttribute("listMostRecentSales", listMostRecentSales);
		long totalOrders = orderDAO.count();
		request.setAttribute("totalOrders", totalOrders);
		
		ReviewDAO reviewDAO = new ReviewDAO();
		List<Review> listMostRecentReviews = reviewDAO.listMostRecent();
		request.setAttribute("listMostRecentReviews", listMostRecentReviews);
		long totalReviews = reviewDAO.count();
		request.setAttribute("totalReviews", totalReviews);
		
		UserDAO userDAO = new UserDAO();
		long totalUsers = userDAO.count();
		request.setAttribute("totalUsers", totalUsers);
		
		BookDAO bookDAO = new BookDAO();
		long totalBooks = bookDAO.count();
		request.setAttribute("totalBooks", totalBooks);
		
		CustomerDAO customerDAO = new CustomerDAO();
		long totalCustomers = customerDAO.count();
		request.setAttribute("totalCustomers", totalCustomers);
		
		String homepage = "index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
}
