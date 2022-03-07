package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Project;

@Controller
public class HomeController {
	
	@Autowired
	ProjectRepository proRepo;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Value(value = "${version}")
	String version;
	
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		
		List<Project> projects = proRepo.findAll();
		model.addAttribute("projects", projects);
		
		List<ChartData> projectData = proRepo.getProjectStatus();
		
		// convert projectData object into a JSON structure for use in JavaScript
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		model.addAttribute("projectStatusCnt", jsonString);
		
		List<EmployeeProject> employeesProjectCnt = empRepo.employeeProjects();
		model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);
		
		//add version attribute
		model.addAttribute("version", version);
		
		return "main/home";
	}
}
