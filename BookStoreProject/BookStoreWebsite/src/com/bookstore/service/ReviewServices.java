package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.ReviewDAO;
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

}
