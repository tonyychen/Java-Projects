package com.jrp.demo.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrp.demo.domain.Product;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = "/{id}/invoices")
	public String displayUserInvoices(@PathVariable("id") int userId,
			@RequestParam(value = "date", required = false) Date dateOrNull) {
		return "invoice found for user: " + userId + " on the date: " + dateOrNull;
	}

	@RequestMapping("/{userId}/products_as_json")
	public Product displayProductsJson(@PathVariable int userId) {
		return new Product(1, "shoes", 42.99);
	}
}
