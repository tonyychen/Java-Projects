package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;

public class OrderDAOTest {

	private static OrderDAO orderDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		orderDAO = new OrderDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		orderDAO.close();
	}

	@Test
	public void testCreateBookOrder() {
		BookOrder order = new BookOrder();
		
		Customer customer = new Customer();
		customer.setCustomerId(5);
		
		order.setCustomer(customer);
		order.setRecipientName("Nam Ha Minh");
		order.setRecipientPhone("123456789");
		order.setShippingAddress("123 South Street, New York, USA");
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();
		
		orderDetail.setBookOrder(order);
		
		Book book = new Book(11);
		
		orderDetail.setBook(book);
		orderDetail.setQuantity(1);
		orderDetail.setSubtotal(100f);
				
		orderDetails.add(orderDetail);
		
		order.setOrderDetails(orderDetails);
		order.setTotal(100f);
		
		BookOrder savedOrder = orderDAO.create(order);
		
		assertTrue(savedOrder != null && savedOrder.getOrderDetails().size() > 0);
	}

	@Test
	public void testCreateBookOrder2() {
		BookOrder order = new BookOrder();
		
		Customer customer = new Customer();
		customer.setCustomerId(8);
		
		order.setCustomer(customer);
		order.setRecipientName("Nam Ha Minh");
		order.setRecipientPhone("123456789");
		order.setShippingAddress("123 South Street, New York, USA");
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();
		
		orderDetail.setBookOrder(order);
		
		Book book1 = new Book(3);
		
		orderDetail.setBook(book1);
		orderDetail.setQuantity(2);
		orderDetail.setSubtotal(50f);
				
		orderDetails.add(orderDetail);
		
		
		OrderDetail orderDetail2 = new OrderDetail();
		
		orderDetail2.setBookOrder(order);
		
		Book book2 = new Book(4);
		
		orderDetail2.setBook(book2);
		orderDetail2.setQuantity(1);
		orderDetail2.setSubtotal(40f);
				
		orderDetails.add(orderDetail2);
		
		
		
		order.setOrderDetails(orderDetails);
		
		BookOrder savedOrder = orderDAO.create(order);
		
		assertTrue(order.getOrderId() > 0 && order.getOrderDetails().size() == 2);
	}
	
	@Test
	public void testUpdateBookOrderShippingAddress() {
		Integer orderId = 9;
		BookOrder order = orderDAO.get(orderId);
		order.setShippingAddress("New Shipping Address");
		
		orderDAO.update(order);
		
		BookOrder updatedOrder = orderDAO.get(orderId);
		
		assertEquals(order.getShippingAddress(), updatedOrder.getShippingAddress());
	}
	
	@Test
	public void testUpdateBookOrderDetail() {
		Integer orderId = 8;
		BookOrder order = orderDAO.get(orderId);
		
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if (orderDetail.getBook().getBookId() == 2) {
				orderDetail.setQuantity(10);
				orderDetail.setSubtotal(120);
			}
		}
		
		orderDAO.update(order);
		
		BookOrder updatedOrder = orderDAO.get(orderId);
		
		int expectedQuantity = 10;
		float expectedSubtotal = 120f;
		
		int actualQuantity = -1;
		float actualSubtotal = -1f;
		
		order = orderDAO.get(orderId);
		iterator = order.getOrderDetails().iterator();
		
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if (orderDetail.getBook().getBookId() == 2) {
				actualQuantity = orderDetail.getQuantity();
				actualSubtotal = orderDetail.getSubtotal();
			}
		}
		
		assertTrue(expectedQuantity == actualQuantity && expectedSubtotal == actualSubtotal);
	}
	
	@Test
	public void testGet() {
		Integer orderId = 9;
		BookOrder order = orderDAO.get(orderId);
		System.out.println(order.getRecipientName());
		System.out.println(order.getRecipientPhone());
		System.out.println(order.getShippingAddress());
		System.out.println(order.getStatus());
		System.out.println(order.getTotal());
		System.out.println(order.getPaymentMethod());
		
		assertEquals(2, order.getOrderDetails().size());
	}
	
	@Test
	public void testGetByIdAndCustomerNull() {
		Integer orderId = 10;
		Integer customerId = 99;
		
		BookOrder order = orderDAO.get(orderId, customerId);
		
		assertNull(order);
	}
	
	@Test
	public void testGetByIdAndCustomerNotNull() {
		Integer orderId = 10;
		Integer customerId = 4;
		
		BookOrder order = orderDAO.get(orderId, customerId);
		
		assertNotNull(order);
	}

	@Test
	public void testDeleteOrder() {
		int orderId = 9;
		orderDAO.delete(orderId);
		
		BookOrder order = orderDAO.get(orderId);
		
		assertNull(order);
	}

	@Test
	public void testListAll() {
		List<BookOrder> listOrders = orderDAO.listAll();
		
		for (BookOrder order : listOrders) {
			System.out.println(order.getOrderId() + " - " + order.getCustomer().getFullname()
					+ " - " + order.getTotal() + " - " + order.getStatus());
			for (OrderDetail detail : order.getOrderDetails()) {
				Book book = detail.getBook();
				int quantity = detail.getQuantity();
				float subtotal = detail.getSubtotal();
				System.out.println("\t" + book.getTitle() + " - " + quantity + " - " + subtotal);
			}
		}
		
		assertEquals(2, listOrders.size());
	}

	@Test
	public void testCount() {
		long totalOrders = orderDAO.count();
		
		assertEquals(2, totalOrders);
	}
	
	@Test
	public void testListByCustomerNoOrders() {
		Integer customerId = 99;
		List<BookOrder> listOrders = orderDAO.listByCustomer(customerId);
		
		assertTrue(listOrders.isEmpty());
	}
	
	@Test
	public void testListByCustomerHaveOrders() {
		Integer customerId = 3;
		List<BookOrder> listOrders = orderDAO.listByCustomer(customerId);
		
		assertTrue(!listOrders.isEmpty());
	}
	
	@Test
	public void testListMostRecentSales() {
		List<BookOrder> recentOrders = orderDAO.listMostRecentSales();
		
		assertEquals(3, recentOrders.size());
	}

}
