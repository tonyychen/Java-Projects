package com.bookstore.controller.frontend.shoppingcart;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update_cart")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] arrayBookIds = request.getParameterValues("bookId");
		String[] arrayQuantities = new String[arrayBookIds.length];
		
		for (int i = 0; i < arrayBookIds.length; i++) {
			String quantity = request.getParameter("quantityFor" + arrayBookIds[i]);
			arrayQuantities[i] = quantity;
		}

		int[] bookIds = Arrays.stream(arrayBookIds).mapToInt(Integer::parseInt).toArray();
		int[] quantities = Arrays.stream(arrayQuantities).mapToInt(Integer::parseInt).toArray();
		
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
		cart.updateCart(bookIds, quantities);
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
