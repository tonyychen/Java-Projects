package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.UserDAO;
import com.bookstore.entity.Users;
import com.bookstore.utility.HashGenerationException;
import com.bookstore.utility.HashGenerator;

public class UserServices {
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		userDAO = new UserDAO();
	}

	// public List<Users> listUser() {
	// return userDAO.listAll();
	// }

	public void listUser() throws ServletException, IOException {
		listUser(null);
	}

	public void listUser(String message) throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();

		request.setAttribute("listUsers", listUsers);

		CommonUtility.forwardToPage(request, response, "user_list.jsp", message);
	}

	public void createUser() throws ServletException, IOException, HashGenerationException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = HashGenerator.generateMD5(request.getParameter("password"));

		Users existUser = userDAO.findByEmail(email);
		if (existUser != null) {
			String message = "Could not create user. A user with email " + email + " already exists";
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}
	}

	public void editUser() throws ServletException, IOException {
		Integer userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAO.get(userId);

		if (user == null) {
			String message = "Could not find user with ID " + userId;
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			request.setAttribute("user", user);
			CommonUtility.forwardToPage(request, response, "user_form.jsp");
		}

	}

	public void updateUser() throws ServletException, IOException, HashGenerationException {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");


		Users userById = userDAO.get(userId);
		Users userByEmail = userDAO.findByEmail(email);

		if (userById == null) {
			String message = "Could not find user with ID " + userId;
			CommonUtility.showMessageBackend(request, response, message);
		} else if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "Could not update user. User with email " + email + " already exists.";
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			Users user = userDAO.get(userId);
			user.setEmail(email);
			user.setFullName(fullName);
			
			if (password != null && !password.equals("")) {
				user.setPassword(HashGenerator.generateMD5(password));
			}

			userDAO.update(user);

			String message = "User has been updated successfully";
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		if (userId == 1) {
			String message = "The default admin user account cannot be deleted";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		Users user = userDAO.get(userId);

		if (user == null) {
			String message = "Could not find user with ID " + userId;
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			userDAO.delete(userId);

			String message = "User has been deleted successfully";
			listUser(message);
		}
	}
	
	public void login() throws ServletException, IOException, HashGenerationException {
		String email = request.getParameter("email");
		String password = HashGenerator.generateMD5(request.getParameter("password"));
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		if (loginResult) {
			request.getSession().setAttribute("useremail", email);;
			
			CommonUtility.forwardToPage(request, response, "/admin/");
		} else {
			String message = "Login failed!";
			CommonUtility.forwardToPage(request, response, "login.jsp", message);
		}
	}
}
