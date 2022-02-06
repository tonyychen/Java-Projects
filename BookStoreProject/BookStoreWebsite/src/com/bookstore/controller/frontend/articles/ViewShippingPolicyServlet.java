package com.bookstore.controller.frontend.articles;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.service.CommonUtility;

/**
 * Servlet implementation class ViewShippingPolicyServlet
 */
@WebServlet("/shipping_policy")
public class ViewShippingPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommonUtility.forwardToPage(request, response, "frontend/shipping_policy.jsp");
	}

}
