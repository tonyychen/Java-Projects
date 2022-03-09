package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrp.pma.entities.Employee;
import com.jrp.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService empService;
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		Employee employee = new Employee();
		
		model.addAttribute("employee", employee);

		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(Employee employee, Model model) {
		//this should handle saving to the database
		empService.save(employee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees";
	}
	
	@GetMapping
	public String listEmployees(Model model) {
		List<Employee> employees = empService.getAll();
		
		model.addAttribute("employees", employees);
		
		return "employees/list-employees";
	}
}
