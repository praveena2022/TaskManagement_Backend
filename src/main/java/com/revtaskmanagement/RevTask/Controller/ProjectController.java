package com.revtaskmanagement.RevTask.Controller;

import com.revtaskmanagement.RevTask.DTO.ProjectCustom1DTO;
import com.revtaskmanagement.RevTask.DTO.ProjectCustom2DTO;
import com.revtaskmanagement.RevTask.DTO.ProjectDTO;
import com.revtaskmanagement.RevTask.DTO.TeamMemberCustomDTO;
import com.revtaskmanagement.RevTask.Entity.Project;
import com.revtaskmanagement.RevTask.Entity.Task;
import com.revtaskmanagement.RevTask.Repository.TeamMemberRepository;
import com.revtaskmanagement.RevTask.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamMemberRepository teamMemberRepository;


    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectService.getProjectDTOById(id);
    }

//    @PostMapping("/create-project")
//    public Project createProject(@RequestBody Project project, @RequestParam Long projectManagerId) {
//        return projectService.createProject(project,projectManagerId);
//    }


//    public ResponseEntity<Project> createProject(@RequestParam Long projectManagerId, @RequestBody ProjectDTO projectDTO) {
//        Project createdProject = projectService.createProject(projectManagerId,projectDTO);
//        return ResponseEntity.ok(createdProject);
//    }
@GetMapping("/PM/{projectManagerId}")
public ResponseEntity<List<ProjectDTO>> getProjectsByProjectManagerId(@PathVariable Long projectManagerId) {
    List<ProjectDTO> projects = projectService.getProjectsByProjectManagerId(projectManagerId);
    return ResponseEntity.ok(projects);
}

    @GetMapping("/TM/{teamMemberId}")
    public ResponseEntity<List<ProjectCustom1DTO>> getProjectsByteamMembersId(@PathVariable Long teamMemberId) {
        List<ProjectCustom1DTO> projects = projectService.getProjectsByTeamMemberId(teamMemberId);

        return ResponseEntity.ok(projects);
    }
//@GetMapping("/TM")
//public ResponseEntity<List<ProjectDTO>> getProjectsByTeamMemberId() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////    String username = authentication.getName();
//    Long teamMemberId = teamMemberRepository.findIdByUsername(username);
//
//    List<ProjectDTO> projects = projectService.getProjectsByTeamMemberId(teamMemberId);
//    return ResponseEntity.ok(projects);
//}
    @PostMapping("/create-project")
    public ResponseEntity<Map<String, String>> createProject(@RequestParam Long projectManagerId, @RequestBody ProjectCustom2DTO projectDTO) {
        try {

            projectService.createProject(projectManagerId, projectDTO);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Project created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "An internal error occurred");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectService.updateProject(id, projectDetails);
    }

    @GetMapping("/{id}/teammembers")
    public List<TeamMemberCustomDTO> getTeamMembersByProjectId(@PathVariable Long id) {
        return projectService.getTeamMembersByProjectId(id);
    }


//    @RequestHeader("userId")
    @GetMapping("/teammembers")
    public List<ProjectCustom1DTO> getTeamMembersByProject() {
        return projectService.getAllProjectsMembers();
    }

//
//    @PostMapping
//    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
//        // Assuming you have a method to convert ProjectDTO to Project entity and save
//        return projectService.createProject(projectDTO);
//    }
//
//    @PutMapping("/{id}")
//    public Project updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
//        // Assuming you have a method to update Project entity
//        return projectService.updateProject(id, projectDetails);
//    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }


    @PostMapping("/{projectId}/add/{memberId}")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long projectId, @PathVariable Long memberId) {
        projectService.addMemberToProject(projectId, memberId);
        return ResponseEntity.ok().build();
    }
}


//import com.revtaskmanagement.RevTask.DTO.ProjectDTO;
//import com.revtaskmanagement.RevTask.Entity.Project;
//import com.revtaskmanagement.RevTask.Service.ProjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/projects")
//public class ProjectController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
//
//    @Autowired
//    private ProjectService projectService;
//
//    @GetMapping("/allprojects")
//    public List<ProjectDTO> getAllProjects() {
//        logger.info("Fetching all projects");
//        List<ProjectDTO> projects = projectService.getAllProjects();
//        if (projects.isEmpty()) {
//            logger.warn("No projects found");
//        } else {
//            logger.info("Projects fetched successfully");
//        }
//        return projects;
//    }
//
//    @GetMapping("/{id}")
//    public ProjectDTO getProjectById(@PathVariable Long id) {
//        logger.info("Fetching project with id: {}", id);
//        ProjectDTO projectDTO = projectService.getProjectDTOById(id);
//        if (projectDTO == null) {
//            logger.error("Project with id: {} not found", id);
//        } else {
//            logger.info("Project with id: {} fetched successfully", id);
//        }
//        return projectDTO;
//    }
//
//    @PostMapping("/project")
//    public Project createProject(@RequestBody Project project) {
//        logger.info("Creating new project: {}", project);
//        Project createdProject = projectService.createProject(project);
//        logger.info("Project created successfully: {}", createdProject);
//        return createdProject;
//    }
//
//    @PutMapping("/{id}")
//    public Project updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
//        logger.info("Updating project with id: {}", id);
//        Project updatedProject = projectService.updateProject(id, projectDetails);
//        logger.info("Project with id: {} updated successfully", id);
//        return updatedProject;
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteProject(@PathVariable Long id) {
//        logger.info("Deleting project with id: {}", id);
//        projectService.deleteProject(id);
//        logger.info("Project with id: {} deleted successfully", id);
//    }
//}

