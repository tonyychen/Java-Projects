package com.bookstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest {

	private static BookDAO bookDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bookDao = new BookDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		bookDao.close();
	}

	@Test
	public void testUpdateBook() throws ParseException, IOException {
		Book existBook = generateTestBook();
		existBook.setBookId(1);
		
		Category category = new Category("Java Core");
		category.setCategoryId(1);
		existBook.setCategory(category);
		
		existBook.setTitle("Effective Java (3rd Edition)");
		existBook.setPrice(40f);
		
		Book updatedBook = bookDao.update(existBook);
		
		assertEquals(updatedBook.getTitle(), "Effective Java (3rd Edition)");
	}

	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book newBook = generateTestBook();
		Book createdBook = bookDao.create(newBook);

		assertTrue(createdBook.getBookId() > 0);
	}
	
	@Test
	public void testCreate2ndBook() throws ParseException, IOException {
		Book newBook = generateTestBook2();
		Book createdBook = bookDao.create(newBook);

		assertTrue(createdBook.getBookId() > 0);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteBookFail() {
		Integer bookId = 100;
		bookDao.delete(bookId);
	}
	
	@Test
	public void testDeleteBookSuccess() {
		Integer bookId = 1;
		bookDao.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testGetBookFail() {
		Integer bookId = 99;
		Book book = bookDao.get(bookId);
		
		assertNull(book);
	}
	
	@Test
	public void testGetBookSuccess() {
		Integer bookId = 2;
		Book book = bookDao.get(bookId);
		
		assertNotNull(book);
	}
	
	@Test
	public void testListAll() {
		List<Book> listBooks = bookDao.listAll();
		
		for(Book book : listBooks) {
			System.out.println(book.getTitle() + " - " + book.getAuthor());
		}
		
		assertTrue(!listBooks.isEmpty());
	}
	
	@Test
	public void testFindByTitleNotExist() {
		String title = "Thinking in Java";
		Book book = bookDao.findByTitle(title);
		
		assertNull(book);
	}
	
	@Test
	public void testFindByTitleExist() {
		String title = "Java 8 in Action";
		Book book = bookDao.findByTitle(title);
		
		System.out.println(book.getAuthor());
		System.out.println(book.getPrice());
		
		assertNotNull(book);
	}
	
	@Test
	public void testCount() {
		long totalBooks = bookDao.count();
		
		assertEquals(2, totalBooks);
	}
	
	@Test
	public void testListByCategory() {
		int categoryId = 4;
		
		List<Book> listBooks = bookDao.listByCategory(categoryId);
		
		assertTrue(listBooks.size() > 0);
	}
	
	@Test
	public void testSearchBookInTitle() {
		String keyword = "Java";
		List<Book> result = bookDao.search(keyword);
		
		assertEquals(6, result.size());
	}
	
	@Test
	public void testSearchBookInAuthor() {
		String keyword = "Kathy";
		List<Book> result = bookDao.search(keyword);
		
		assertEquals(2, result.size());
	}
	
	@Test
	public void testSearchBookInDescription() {
		String keyword = "The big picture";
		List<Book> result = bookDao.search(keyword);
		
		assertEquals(1, result.size());
	}
	
	@Test
	public void testListNewBooks() {
		List<Book> listNewBooks = bookDao.listNewBooks();
		
		for (Book aBook : listNewBooks) {
			System.out.println(aBook.getTitle() + " - " + aBook.getPublishDate());
		}
		
		assertEquals(4, listNewBooks.size());
	}
	
	public Book generateTestBook() throws ParseException, IOException {
		Book newBook = new Book();

		Category category = new Category("Advanced Java");
		category.setCategoryId(2);
		newBook.setCategory(category);

		newBook.setTitle("Effective Java (2nd Edition)");
		newBook.setAuthor("Joshua Bloch");
		newBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		newBook.setPrice(38.87f);
		newBook.setIsbn("0321356683");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		newBook.setPublishDate(publishDate);

		String imagePath = "C:\\Users\\Tony\\Dropbox\\Java-Projects\\BookStoreProject\\books\\Effective Java.JPG";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);

		return newBook;
	}
	
	public Book generateTestBook2() throws ParseException, IOException {
		Book newBook = new Book();

		Category category = new Category("Java Core");
		category.setCategoryId(1);
		newBook.setCategory(category);

		newBook.setTitle("Java 8 in Action");
		newBook.setAuthor("Alan Mycroft");
		newBook.setDescription("Java 8 in Action is a clearly written guide to the new features of Java 8");
		newBook.setPrice(36.72f);
		newBook.setIsbn("1617291994");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("08/28/2014");
		newBook.setPublishDate(publishDate);

		String imagePath = "C:\\Users\\Tony\\Dropbox\\Java-Projects\\BookStoreProject\\books\\Java 8 in Action.jpg";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);

		return newBook;
	}


}
