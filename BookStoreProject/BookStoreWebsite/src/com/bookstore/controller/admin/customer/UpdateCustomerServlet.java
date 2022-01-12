package com.bookstore.controller.admin.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.service.CustomerServices;
import com.bookstore.utility.HashGenerationException;

@WebServlet("/admin/update_customer")
public class UpdateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		try {
			customerServices.updateCustomer();
		} catch (HashGenerationException e) {
			throw new ServletException("issue with password hash generation", e);
		}
	}

}
