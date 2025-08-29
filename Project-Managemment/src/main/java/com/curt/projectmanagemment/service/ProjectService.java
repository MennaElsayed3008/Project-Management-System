package com.curt.projectmanagemment.service;

import com.curt.projectmanagemment.entities.Project;
import com.curt.projectmanagemment.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id " + id));
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id);
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setCreatedAt(updatedProject.getCreatedAt());
        return projectRepository.save(project);
    }

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
