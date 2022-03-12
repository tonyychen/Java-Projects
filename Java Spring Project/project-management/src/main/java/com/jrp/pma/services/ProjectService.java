package com.jrp.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entities.Project;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository proRepo;
	
	public List<Project> getAll() {
		return proRepo.findAll();
	}
	
	public List<ChartData> getProjectStatus() {
		return proRepo.getProjectStatus();
	}
	
	public Project save(Project project) {
		return proRepo.save(project);
	}
	
	public Project findById(Long id) {
		return proRepo.findById(id).get();
	}
	
	public void deleteById(Long id) {
		proRepo.deleteById(id);
	}
	
	public List<TimeChartData> getTimeData() {
		return proRepo.getTimeData();
	}
	
}
