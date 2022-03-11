package com.jrp.pma.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jrp.pma.entities.Project;
import com.jrp.pma.services.ProjectService;

@RestController
@RequestMapping("/app-api/projects")
public class ProjectApiController {

	@Autowired
	ProjectService proService;

	@GetMapping
	public List<Project> getProjects() {
		return proService.getAll();
	}

	@GetMapping("/{id}")
	public Project getProject(@PathVariable("id") Long id) {
		return proService.findById(id);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Project addProject(@RequestBody Project project) {
		return proService.save(project);
	}

	@PutMapping(path = "/{id}", consumes = "application/json")
	public Project updateProject(@PathVariable("id") Long id, @RequestBody Project project) {
		project.setProjectId(id);
		return proService.save(project);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Project patchProject(@PathVariable("id") Long id, @RequestBody Project project) {
		Project curProject = proService.findById(id);

		if (project.getName() != null) {
			curProject.setName(project.getName());
		}

		if (project.getStage() != null) {
			curProject.setStage(project.getStage());
		}

		if (project.getDescription() != null) {
			curProject.setDescription(project.getDescription());
		}

		return proService.save(curProject);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProject(@PathVariable("id") Long id) {
		try {
			proService.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

		}
	}
}
