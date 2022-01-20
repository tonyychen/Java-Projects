package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

public class ReviewServices {
	private ReviewDAO reviewDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		reviewDAO = new ReviewDAO();
	}

	public void listAllReview(String message) throws ServletException, IOException {
		List<Review> listReviews = reviewDAO.listAll();

		request.setAttribute("listReviews", listReviews);

		CommonUtility.forwardToPage(request, response, "review_list.jsp", message);
	}

	public void listAllReview() throws ServletException, IOException {
		listAllReview(null);
	}

	public void editReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);

		if (review == null) {
			String message = "Could not find review with ID " + reviewId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		request.setAttribute("review", review);

		CommonUtility.forwardToPage(request, response, "review_form.jsp");
	}

	public void updateReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));

		Review review = reviewDAO.get(reviewId);
		if (review == null) {
			String message = "Could not find review with ID " + reviewId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");

		review.setHeadline(headline);
		review.setComment(comment);

		reviewDAO.update(review);

		String message = "The review has been updated successfully";
		listAllReview(message);
	}

	public void deleteReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);
		if (review == null) {
			String message = "Could not find review with ID " + reviewId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		reviewDAO.delete(reviewId);

		String message = "The review has been deleted successfully.";

		listAllReview(message);
	}

	public void showReviewForm() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("book_id"));
		BookDAO bookDao = new BookDAO();
		Book book = bookDao.get(bookId);
		request.setAttribute("book", book);
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		Review existReview = reviewDAO.findByCustomerAndBook(customer.getCustomerId(), bookId);
		
		String targetPage = "frontend/review_form.jsp";
		
		if (existReview != null) {
			request.setAttribute("review", existReview);
			targetPage = "frontend/review_info.jsp";
		}

		CommonUtility.forwardToPage(request, response, targetPage);
	}

	public void submitReview() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
		
		Review newReview = new Review();
		newReview.setHeadline(headline);
		newReview.setComment(comment);
		newReview.setRating(rating);
		
		Book book = new Book();
		book.setBookId(bookId);
		newReview.setBook(book);
		
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		newReview.setCustomer(customer);
		
		reviewDAO.create(newReview);
		
		String messagePage = "frontend/review_done.jsp";
		CommonUtility.forwardToPage(request, response, messagePage);
	}

}
