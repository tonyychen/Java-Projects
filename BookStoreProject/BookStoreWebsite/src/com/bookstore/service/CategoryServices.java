package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class CategoryServices {
	private CategoryDAO categoryDAO;
	private BookDAO bookDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CategoryServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		categoryDAO = new CategoryDAO();
		bookDAO = new BookDAO();
	}

	public void listCategory(String message) throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();

		request.setAttribute("listCategory", listCategory);
		
		CommonUtility.forwardToPage(request, response, "category_list.jsp", message);
	}

	public void listCategory() throws ServletException, IOException {
		listCategory(null);
	}

	public void createCategory() throws ServletException, IOException {
		String name = request.getParameter("name");
		Category existCategory = categoryDAO.findByName(name);

		if (existCategory != null) {
			String message = "Could not create category. " + "A category with name " + name + " already exists.";
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			Category newCategory = new Category(name);
			categoryDAO.create(newCategory);
			String message = "New category created successfully";
			listCategory(message);
		}
	}

	public void editCategory() throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Could not find category with ID " + categoryId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		request.setAttribute("category", category);

		CommonUtility.forwardToPage(request, response, "category_form.jsp");
	}

	public void updateCategory() throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("name");

		Category categoryById = categoryDAO.get(categoryId);
		Category categoryByName = categoryDAO.findByName(categoryName);

		if (categoryById == null) {
			String message = "Could not find category with ID " + categoryId;
			CommonUtility.showMessageBackend(request, response, message);
		} else if (categoryByName != null && categoryById.getCategoryId() != categoryByName.getCategoryId()) {
			String message = "Could not update category. " + "A category with name " + categoryName + " aready exists.";
			CommonUtility.showMessageBackend(request, response, message);
		} else {
			categoryById.setName(categoryName);
			categoryDAO.update(categoryById);
			String message = "Category has been updated successfully.";
			listCategory(message);
		}
	}

	public void deleteCategory() throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Could not find category with ID " + categoryId;
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}
		
		List<Book> booksInCategory = bookDAO.listByCategory(categoryId);
		if (booksInCategory != null && booksInCategory.size() > 0) {
			String message = "Could not delete category with ID " + categoryId 
					+ " because there are books in the category";
			CommonUtility.showMessageBackend(request, response, message);
			return;
		}

		categoryDAO.delete(categoryId);

		String message = "The category with ID " + categoryId + " has been removed successfully.";
		listCategory(message);
	}

}
