package com.jrp.pma.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		Project aProject = new Project();
		
		List<Employee> employees = empService.getAll();
		
		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);

		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Model model, @Valid Project project, Errors errors) {
		if (errors.hasErrors()) {
			List<Employee> employees = empService.getAll();
			model.addAttribute("allEmployees", employees);
			
			return "projects/new-project";
		}
		
		//this should handle saving to the database
		proService.save(project);

		// use a redirect to prevent duplicate submissions
		return "redirect:/projects";
	}
	
	@GetMapping
	public String listProjects(Model model) {
		List<Project> projects = proService.getAll();
		
		model.addAttribute("projects", projects);
		
		return "projects/list-projects";
	}
	
	@GetMapping("/update")
	public String displayUpdateProjectForm(@RequestParam("id") Long id, Model model) {
		Project project = proService.findById(id);
		
		List<Employee> employees = empService.getAll();
		
		model.addAttribute("allEmployees", employees);
		
		model.addAttribute("project", project);
		
		return "projects/new-project";
	}
	
	@GetMapping("/delete")
	public String deleteProject(@RequestParam("id") Long id) {
		proService.deleteById(id);
		
		return "redirect:/projects";
	}
	
	@GetMapping("/timelines")
	public String displayProjectTimelines(Model model) throws JsonProcessingException {
		List<TimeChartData> timelineData = proService.getTimeData();
		
		// convert timelineData object into a JSON structure for use in JavaScript
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonTimelineString = objectMapper.writeValueAsString(timelineData);
		
		System.out.println("---------------- project timelnes -----------------");
		System.out.println(jsonTimelineString);
		
		model.addAttribute("projectTimeList", jsonTimelineString);
		
		return "projects/project-timelines";
	}
}
