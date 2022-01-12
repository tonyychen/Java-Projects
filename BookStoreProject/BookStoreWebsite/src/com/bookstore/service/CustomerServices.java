package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.CustomerDAO;
import com.bookstore.entity.Customer;
import com.bookstore.utility.HashGenerationException;
import com.bookstore.utility.HashGenerator;

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

	public void createCustomer() throws ServletException, IOException, HashGenerationException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);

		if (existCustomer != null) {
			String message = "Could not create customer: the email " + email
					+ " is already registered by another customer.";
			listCustomers(message);
		} else {
			Customer newCustomer = new Customer();

			updateCustomerFieldsFromForm(newCustomer);

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

	public void updateCustomer() throws ServletException, IOException, HashGenerationException {
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
			updateCustomerFieldsFromForm(customerById);

			customerDAO.update(customerById);

			message = "The customer has been updated successfully.";
		}

		listCustomers(message);
	}

	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		if (customerDAO.get(customerId) == null) {
			String message = "Could not find customer with ID " + customerId
					+ ", or it has been deleted by another admin";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		customerDAO.delete(customerId);

		String message = "The customer has been deleted successfully.";
		listCustomers(message);
	}

	public void registerCustomer() throws ServletException, IOException, HashGenerationException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);

		String message = null;

		if (existCustomer != null) {
			message = "Could not register: the email " + email + " is already registered by another customer.";
		} else {
			Customer newCustomer = new Customer();

			updateCustomerFieldsFromForm(newCustomer);

			customerDAO.create(newCustomer);

			message = "You have registered successfully! Thank you.<br/>" + "<a href='login'>Click Here</a> to login";
		}

		CommonUtility.showMessageFrontend(request, response, message);
	}

	private void updateCustomerFieldsFromForm(Customer customer) throws HashGenerationException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipCode = request.getParameter("zipCode");
		String country = request.getParameter("country");

		customer.setEmail(email);
		customer.setFullname(fullName);
		customer.setPassword(HashGenerator.generateMD5(password));
		customer.setPhone(phone);
		customer.setAddress(address);
		customer.setCity(city);
		customer.setZipcode(zipCode);
		customer.setCountry(country);
	}

	public void showLogin() throws ServletException, IOException {
		CommonUtility.forwardToPage(request, response, "frontend/login.jsp");
	}

	public void doLogin() throws ServletException, IOException, HashGenerationException {
		String email = request.getParameter("email");
		String password = HashGenerator.generateMD5(request.getParameter("password"));
		
		Customer customer = customerDAO.checkLogin(email, password);
		
		if (customer == null) {
			String message = "Login failed. Please check your email and password";
			CommonUtility.forwardToPage(request, response, "frontend/login.jsp", message);
		} else {
			request.getSession().setAttribute("loggedCustomer", customer);
			showCustomerProfile();
		}
	}
	
	public void showCustomerProfile() throws ServletException, IOException {
		CommonUtility.forwardToPage(request, response, "frontend/customer_profile.jsp");
	}
}
