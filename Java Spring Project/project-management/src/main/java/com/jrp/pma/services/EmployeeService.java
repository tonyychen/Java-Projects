package com.jrp.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository empRepo;
	
	public Employee save(Employee employee) {
		return empRepo.save(employee);
	}
	
	public List<Employee> getAll() {
		return empRepo.findAll();
	}
	
	public Iterable<Employee> getAll(Pageable pageAndSize) {
		return empRepo.findAll(pageAndSize);
	}
	
	public List<EmployeeProject> employeeProjects() {
		return empRepo.employeeProjects();
	}
	
	public Employee findById(Long id) {
		return empRepo.findById(id).get();
	}

	public void deleteById(Long id) {
		empRepo.deleteById(id);		
	}
	
	public Employee findByEmail(String email) {
		return empRepo.findByEmail(email);
	}
}
