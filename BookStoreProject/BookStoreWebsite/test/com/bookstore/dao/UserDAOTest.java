package com.bookstore.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static UserDAO userDAO;

	@BeforeClass
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
		entityManager = entityManagerFactory.createEntityManager();

		userDAO = new UserDAO(entityManager);
	}

	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("tommy@gmail.com");
		user1.setFullName("Tommy Timothy");
		user1.setPassword("abcdefghij");

		user1 = userDAO.create(user1);

		assertTrue(user1.getUserId() > 0); // UserId will have value if persistence is successful
	}

	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();
		user1 = userDAO.create(user1);

		assertTrue(user1.getUserId() > 0); // UserId will have value if persistence is successful
	}
	
	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(1);
		user.setEmail("nam@codejava.net");
		user.setFullName("Nam Ha Minh");
		user.setPassword("mysecret");
		
		user = userDAO.update(user);
		
		//This is incorrect, just a temporary solution!
		String expected = "mysecret";
		String actual = user.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@AfterClass
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
