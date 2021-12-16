package com.bookstore.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonUtility {
	public static void forwardToPage(HttpServletRequest request, HttpServletResponse response, String pageName,
			String message) throws ServletException, IOException {
		if (message != null) {
			request.setAttribute("message", message);
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(pageName);
		requestDispatcher.forward(request, response);
	}

	public static void forwardToPage(HttpServletRequest request, HttpServletResponse response, String pageName)
			throws ServletException, IOException {
		forwardToPage(request, response, pageName, null);
	}

	public static void showMessageFrontend(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		forwardToPage(request, response, "frontend/message.jsp", message);
	}

	public static void showMessageBackend(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		forwardToPage(request, response, "message.jsp", message);
	}
}
