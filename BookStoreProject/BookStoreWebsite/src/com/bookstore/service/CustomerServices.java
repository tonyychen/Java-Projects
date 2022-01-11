package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.CustomerDAO;
import com.bookstore.entity.Customer;

public class CustomerServices {
	private CustomerDAO customerDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.customerDAO = new CustomerDAO();
	}

	public void listCustomers(String message) throws ServletException, IOException {
		List<Customer> listCustomer = customerDAO.listAll();

		request.setAttribute("listCustomer", listCustomer);
		
		CommonUtility.forwardToPage(request, response, "customer_list.jsp", message);
	}
	
	public void listCustomers() throws ServletException, IOException {
		listCustomers(null);
	}

	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		
		if (existCustomer != null) {
			String message = "Could not create customer: the email " + email
					+ " is already registered by another customer.";
			listCustomers(message);
		} else {
			String fullName = request.getParameter("fullName");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String zipCode = request.getParameter("zipCode");
			String country = request.getParameter("country");
			
			Customer newCustomer = new Customer();
			newCustomer.setEmail(email);
			newCustomer.setFullname(fullName);
			newCustomer.setPassword(password);
			newCustomer.setPhone(phone);
			newCustomer.setAddress(address);
			newCustomer.setCity(city);
			newCustomer.setZipcode(zipCode);
			newCustomer.setCountry(country);
			
			customerDAO.create(newCustomer);
			
			String message = "New customer has been created successfully.";
			listCustomers(message);
		}
	}

	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		
		if (customer == null) {
			String message = "Could not find customer with ID " + customerId + ".";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}
		
		request.setAttribute("customer", customer);
		
		CommonUtility.forwardToPage(request, response, "customer_form.jsp");
	}

	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");

		Customer customerByEmail = customerDAO.findByEmail(email);
		Customer customerById = customerDAO.get(customerId);
		if (customerById == null) {
			String message = "Could not find customer with ID " + customerId + ".";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}
		
		String message = null;
		if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Could not update the customer ID " + customerId 
					+ " because there is an existing customer customer having the same email.";
		} else {
			String fullName = request.getParameter("fullName");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String zipCode = request.getParameter("zipCode");
			String country = request.getParameter("country");
						
			customerById.setEmail(email);
			customerById.setFullname(fullName);
			customerById.setPassword(password);
			customerById.setPhone(phone);
			customerById.setAddress(address);
			customerById.setCity(city);
			customerById.setZipcode(zipCode);
			customerById.setCountry(country);
			
			customerDAO.update(customerById);
			
			message = "The customer has been updated successfully.";
		}
		
		listCustomers(message);
	}

	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		if (customerDAO.get(customerId) == null) {
			String message = "Could not find customer with ID " + customerId + ", or it has been deleted by another admin";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}
			
		customerDAO.delete(customerId);
		
		String message = "The customer has been deleted successfully.";
		listCustomers(message);
	}
}
