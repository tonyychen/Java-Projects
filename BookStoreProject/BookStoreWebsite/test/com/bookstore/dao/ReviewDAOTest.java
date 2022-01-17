package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

public class ReviewDAOTest {

	private static ReviewDAO reviewDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reviewDao = new ReviewDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		reviewDao.close();
	}

	@Test
	public void testCreateReview() {
		Review review = new Review();
		Book book = new Book();
		Customer customer = new Customer();
		book.setBookId(2);
		customer.setCustomerId(5);

		review.setBook(book);
		review.setCustomer(customer);

		review.setHeadline("Excellent book!");
		review.setRating(5);
		review.setComment("A comprehensive book!");

		Review savedReview = reviewDao.create(review);

		assertTrue(savedReview.getReviewId() > 0);
	}

	@Test
	public void testGet() {
		Integer reviewId = 1;
		Review review = reviewDao.get(reviewId);
		
		assertNotNull(review);
	}
	
	@Test
	public void testUpdateReview() {
		Integer reviewId = 1;
		Review review = reviewDao.get(reviewId);
		
		review.setHeadline("Excellent book");
		
		Review updatedReview = reviewDao.update(review);
		
		assertEquals("Excellent book", updatedReview.getHeadline());
		
	}

	@Test
	public void testDeleteReview() {
		int reviewId = 2;
		reviewDao.delete(reviewId);
		
		Review review = reviewDao.get(reviewId);
		
		assertNull(review);
	}

	@Test
	public void testListAll() {
		List<Review> listReview = reviewDao.listAll();
		
		for (Review review : listReview) {
			System.out.println(review.getReviewId() + " - " + review.getBook().getTitle()
					+ " - " + review.getCustomer().getFullname());
		}
		
		assertTrue(listReview != null && listReview.size() > 0);
	}

	@Test
	public void testCount() {
		long totalReviews = reviewDao.count();
		
		System.out.println("Total Reviews: " + totalReviews);
		
		assertTrue(totalReviews > 0);
	}

}
