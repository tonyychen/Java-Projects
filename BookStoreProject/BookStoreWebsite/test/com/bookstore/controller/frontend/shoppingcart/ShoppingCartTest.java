package com.bookstore.controller.frontend.shoppingcart;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;

public class ShoppingCartTest {
	private static ShoppingCart cart;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cart = new ShoppingCart();
		
		Book book = new Book(1);
		book.setPrice(10);
		
		cart.addItem(book);
		cart.addItem(book);
	}
	
	@Test
	public void testAddItem() {
		ShoppingCart cart = new ShoppingCart();
		
		Book book = new Book(1);
		
		cart.addItem(book);
		cart.addItem(book);
		
		Map<Book, Integer> items = cart.getItems();
		int quantity = items.get(book);
		
		assertEquals(2, quantity);
	}
	
	@Test
	public void testRemoveItem() {
		cart.removeItem(new Book(1));
		
		assertTrue(cart.getItems().isEmpty());
	}
	
	@Test
	public void testRemoveItem2() {
		cart.addItem(new Book(2));
		cart.removeItem(new Book(1));
		
		assertTrue(cart.getItems().size() == 1);
	}
	
	@Test
	public void testGetTotalQuantity() {
		Book book3 = new Book(3);
		cart.addItem(book3);
		cart.addItem(book3);
		cart.addItem(book3);
		
		assertEquals(5, cart.getTotalQuantity());
	}
	
	@Test
	public void testGetTotalAmount1() {
		ShoppingCart cart = new ShoppingCart();
		assertEquals(0, cart.getTotalAmount(), 0);
	}
	
	@Test
	public void testGetTotalAmount2() {
		assertEquals(20, cart.getTotalAmount(), 0);
	}
	
	@Test
	public void testClear() {
		cart.clear();
		
		assertEquals(0, cart.getTotalQuantity());
	}
	
	@Test
	public void testUpdateCart() {
		Book book = new Book(2);
		cart.addItem(book);
		
		int[] bookIds = {1, 2};
		int[] quantities = {3, 4};
		
		cart.updateCart(bookIds, quantities);
		
		assertEquals(7, cart.getTotalQuantity());
	}

}
