package com.jrp.demo.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/greeting")
public class HelloController {
	@RequestMapping(value = "/basic")
	public String sayHello() {
		return "hello";
	}
	
	@RequestMapping(value = "/proper")
	public String sayProperHello() {
		return "hello there, how are you?";
	}
	
	@RequestMapping(value = "/user_entry")
	public String userForm() {
		return
		"<form action=\"/greeting/user_greeting\" method=\"GET\">\r\n"
		+ "  <label for=\"fname\">First name:</label><br>\r\n"
		+ "  <input type=\"text\" id=\"fname\" name=\"fname\"><br>\r\n"
		+ "  <label for=\"lname\">Last name:</label><br>\r\n"
		+ "  <input type=\"text\" id=\"lname\" name=\"lname\"><br><br>\r\n"
		+ "  <input type=\"submit\" value=\"Submit\">\r\n"
		+ "</form> ";
	}
	
	@RequestMapping(value = "/user_greeting", method = RequestMethod.GET)
	
	public String printUserGreeting(@RequestParam String fname, @RequestParam String lname) {
		return "Hello there, " + fname + " " + lname;
	}
	
	@RequestMapping(value = "/orders/{id}", method=RequestMethod.GET)
	public String getOrder(@PathVariable String id) {
		return "Order ID: " + id;
	}
}
