package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import com.bookstore.entity.Review;

public class BookServices {
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.bookDAO = new BookDAO();
		this.categoryDAO = new CategoryDAO();
	}

	public void listBooks() throws ServletException, IOException {
		listBooks(null);
	}

	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);

		CommonUtility.forwardToPage(request, response, "book_list.jsp", message);
	}

	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);

		CommonUtility.forwardToPage(request, response, "book_form.jsp");
	}

	public void createBook() throws ServletException, IOException {
		Book newBook = new Book();
		readBookFields(newBook);

		String title = newBook.getTitle();

		Book existBook = bookDAO.findByTitle(title);

		if (existBook != null) {
			String message = "Could not create new book because the title '" + title + "' already exists.";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		Book createdBook = bookDAO.create(newBook);

		if (createdBook.getBookId() > 0) {
			String message = "A new book has been created successfully.";
			listBooks(message);
		}
	}

	public void readBookFields(Book book) throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = null;

		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("error parsing publish date (format is MM/dd/yyyy)");
		}

		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setIsbn(isbn);
		book.setPublishDate(publishDate);

		Category category = categoryDAO.get(categoryId);
		book.setCategory(category);

		book.setPrice(price);

		Part part = request.getPart("bookImage");

		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();

			book.setImage(imageBytes);
		}
	}

	public void editBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		if (book == null) {
			String message = "Could not find book with ID " + bookId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		List<Category> listCategory = categoryDAO.listAll();

		request.setAttribute("book", book);
		request.setAttribute("listCategory", listCategory);

		CommonUtility.forwardToPage(request, response, "book_form.jsp");
	}

	public void updateBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");

		Book existBook = bookDAO.get(bookId);
		Book bookByTitle = bookDAO.findByTitle(title);

		if (existBook == null) {
			String message = "Could not find book with ID " + bookId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		// there is one bookByTitle and it is not this book
		if (bookByTitle != null && !existBook.equals(bookByTitle)) {
			String message = "Could not update book because there is another book having the same title.";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		readBookFields(existBook);

		bookDAO.update(existBook);

		String message = "The book has been updated successfully.";
		listBooks(message);
	}

	public void deleteBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));

		Book existBook = bookDAO.get(bookId);

		if (existBook == null) {
			String message = "Could not find book with ID " + bookId + ", or it might have been deleted";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		if (bookDAO.hasReviews(bookId)) {
			String message = "Cannot delete book with ID " + bookId
					+ " because the book is associated with one or more reviews";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		bookDAO.delete(bookId);

		String message = "The book has been deleted successfully.";
		listBooks(message);
	}

	public void listBooksByCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Sorry, the category ID " + categoryId + " is not available.";
			CommonUtility.showMessageFrontend(request, response, message);
			return;
		}

		List<Book> listBooks = bookDAO.listByCategory(categoryId);

		request.setAttribute("listBooks", listBooks);
		request.setAttribute("category", category);

		CommonUtility.forwardToPage(request, response, "frontend/books_list_by_category.jsp");
	}

	public void viewBookDetail() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		if (book == null) {
			String message = "Sorry, the book with ID " + bookId + " is not available.";
			CommonUtility.showMessageFrontend(request, response, message);
			return;
		}

		request.setAttribute("book", book);

		CommonUtility.forwardToPage(request, response, "frontend/book_detail.jsp");
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Book> result = null;

		if (keyword.equals("")) {
			result = bookDAO.listAll();
		} else {
			result = bookDAO.search(keyword);
		}

		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);

		CommonUtility.forwardToPage(request, response, "frontend/search_result.jsp");
	}
}
