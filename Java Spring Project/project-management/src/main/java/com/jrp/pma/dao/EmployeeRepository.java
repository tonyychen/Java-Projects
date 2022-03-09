package com.jrp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	@Override
	public List<Employee> findAll();
	
	@Query(nativeQuery=true, value="SELECT e.first_name as firstName, e.last_name as lastName, COUNT(pe.project_id) as projectCount "
			+ "FROM employee AS e "
			+ "LEFT JOIN project_employee AS pe ON pe.employee_id = e.employee_id "
			+ "GROUP BY e.employee_id "
			+ "ORDER BY projectCount DESC; ")
	public List<EmployeeProject> employeeProjects();
}
