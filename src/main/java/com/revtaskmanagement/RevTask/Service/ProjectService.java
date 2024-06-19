package com.revtaskmanagement.RevTask.Service;

import com.revtaskmanagement.RevTask.DTO.*;
import com.revtaskmanagement.RevTask.Entity.Client;
import com.revtaskmanagement.RevTask.Entity.Project;
import com.revtaskmanagement.RevTask.Entity.ProjectManager;
import com.revtaskmanagement.RevTask.Entity.TeamMember;
import com.revtaskmanagement.RevTask.Exception.ResourceNotFoundException;
import com.revtaskmanagement.RevTask.Repository.ClientRepository;
import com.revtaskmanagement.RevTask.Repository.ProjectManagerRepository;
import com.revtaskmanagement.RevTask.Repository.ProjectRepository;
import com.revtaskmanagement.RevTask.Repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public ProjectDTO getProjectDTOById(Long id) {
        logger.info("Fetching project with id: {}", id);
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            logger.info("Project found with id: {}", id);
            return mapToDTO(projectOpt.get());
        } else {
            logger.error("Project not found with id: {}", id);
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
    }


    public List<ProjectDTO> getAllProjects() {
        logger.info("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        logger.info("Total projects found: {}", projects.size());
        return projects.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<ProjectDTO> getProjectsByProjectManagerId(Long projectManagerId) {
        List<Project> projects = projectRepository.findAllByProjectManagerId(projectManagerId);
        return projects.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProjectCustom1DTO> getProjectsByTeamMemberId(Long teamMemberId) {
        List<Project> projects = projectRepository.findAllByTeamMemberId(teamMemberId);
    logger.info(String.valueOf(projects.stream().count()));
        return projects.stream()
                .map(this::mapToDTOMembers)
                .collect(Collectors.toList());
    }



    //public List<ProjectCustom1DTO> getAllProjectsMembers() {
//    logger.info("Fetching all projects");
//    List<Project> projects = projectRepository.findAll();
//    logger.info("Total projects found: {}", projects.size());
//    return projects.stream()
//            .map(this::mapToDTO)
//            .collect(Collectors.toList());
//}
    public List<ProjectCustom1DTO> getAllProjectsMembers() {
        logger.info("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        logger.info("Total projects found: {}", projects.size());

        return projects.stream()
                .map(this::mapToDTOMembers)
                .collect(Collectors.toList());
    }

    public void deleteProject(Long id) {
        logger.info("Deleting project with id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        projectRepository.delete(project);
        logger.info("Project deleted with id: {}", id);
    }

    private void validateAndSetReferences(Project project) {
        logger.debug("Validating and setting references for project: {}", project);
        if (project.getProjectManager() != null) {
            Optional<ProjectManager> pmOpt = projectManagerRepository.findById(project.getProjectManager().getId());
            pmOpt.ifPresent(project::setProjectManager);
        }
        if (project.getClient() != null) {
            Optional<Client> clientOpt = clientRepository.findById(project.getClient().getId());
            clientOpt.ifPresent(project::setClient);
        }
    }

    ProjectDTO mapToDTO(Project project) {
        logger.debug("Mapping project entity to DTO: {}", project);
        List<TaskDTO> taskDetails = project.getTasks().stream()
                .map(task -> new TaskDTO(task.getId(), task.getTitle()))
                .collect(Collectors.toList());


        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getClient().getName(),
                project.getProjectManager().getId(),
                project.getProjectManager().getUsername(),
                taskDetails
        );
    }

    private ProjectCustom1DTO mapToDTOMembers(Project project) {
        List<TaskDTO> taskDetails = project.getTasks().stream()
                .map(task -> new TaskDTO(task.getId(), task.getTitle()))
                .collect(Collectors.toList());

        List<TeamMemberDTO> teamMemberDetails = project.getTeamMembers().stream()
                .map(this::mapToTeamMemberDTO2)
                .collect(Collectors.toList());

        return new ProjectCustom1DTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getClient().getName(),
                project.getProjectManager().getId(),
                project.getProjectManager().getUsername(),
                taskDetails,
                teamMemberDetails
        );
    }

    public List<TeamMemberCustomDTO> getTeamMembersByProjectId(Long id) {
        logger.info("Fetching team members for project with id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return project.getTeamMembers().stream()
                .map(this::mapToTeamMemberDTO)
                .collect(Collectors.toList());
    }

    private TeamMemberCustomDTO mapToTeamMemberDTO(TeamMember teamMember) {
        return new TeamMemberCustomDTO(
                teamMember.getId(),
                teamMember.getUsername(),
                teamMember.getEmail(),
                teamMember.getRole()
        );
    }

    private TeamMemberDTO mapToTeamMemberDTO2(TeamMember teamMember) {
        return new TeamMemberDTO(
                teamMember.getId(),
                teamMember.getEmail(),
                teamMember.getRole()
        );
    }


    public void createProject(Long projectManagerId,ProjectCustom2DTO projectDTO) {
        Optional<ProjectManager> projectManagerOpt = projectManagerRepository.findById(projectManagerId);
        Optional<Client> client = clientRepository.findById(projectDTO.getClientId());


        if (projectManagerOpt.isPresent()&& client.isPresent()) {
            ProjectManager projectManager = projectManagerOpt.get();
            Client client1 = client.get();


            Project project = new Project();
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setClient(client1);
            project.setDueDate(projectDTO.getDueDate());
            project.setAssignedDate(projectDTO.getAssignedDate());
            project.setProjectManager(projectManager);

            projectRepository.save(project);
        } else {
            throw new RuntimeException("Project manager not found for ID: " + projectManagerId);
        }
    }


    public Project updateProject(Long id, Project projectDetails) {
        logger.info("Updating project with id: {}", id);
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            Project existingProject = projectOpt.get();
            logger.debug("Found existing project: {}", existingProject);
            existingProject.setName(projectDetails.getName());
            existingProject.setDescription(projectDetails.getDescription());
            existingProject.setProjectManager(projectDetails.getProjectManager());
            existingProject.setClient(projectDetails.getClient());
            existingProject.setTeamMembers(projectDetails.getTeamMembers());
            existingProject.setTasks(projectDetails.getTasks());
            validateAndSetReferences(existingProject);
            Project updatedProject = projectRepository.save(existingProject);
            logger.info("Project updated successfully: {}", updatedProject);
            return updatedProject;
        } else {
            logger.error("Project not found with id: {}", id);
            throw new RuntimeException("Project not found");
        }
    }
    public ProjectDTO addMemberToProject(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        TeamMember member = teamMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        project.getTeamMembers().add(member);
        projectRepository.save(project);
        return mapToDTO(project);
    }
}

// Working code

//import com.revtaskmanagement.RevTask.DTO.ProjectDTO;
//import com.revtaskmanagement.RevTask.DTO.TaskDTO;
//import com.revtaskmanagement.RevTask.Entity.Client;
//import com.revtaskmanagement.RevTask.Entity.Project;
//import com.revtaskmanagement.RevTask.Entity.ProjectManager;
//import com.revtaskmanagement.RevTask.Exception.ResourceNotFoundException;
//import com.revtaskmanagement.RevTask.Repository.ClientRepository;
//import com.revtaskmanagement.RevTask.Repository.ProjectManagerRepository;
//import com.revtaskmanagement.RevTask.Repository.ProjectRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ProjectService {
//    @Autowired
//    private ProjectRepository projectRepository;
//    @Autowired
//    private ProjectManagerRepository projectManagerRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//
//    public ProjectDTO getProjectDTOById(Long id) {
//        Optional<Project> projectOpt = projectRepository.findById(id);
//        if (projectOpt.isPresent()) {
//            return mapToDTO(projectOpt.get());
//        } else {
//            throw new ResourceNotFoundException("Project not found with id: " + id);
//        }
//    }
//
//    public List<ProjectDTO> getAllProjects() {
//        List<Project> projects = projectRepository.findAll();
//        return projects.stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public void deleteProject(Long id) {
//        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//        projectRepository.delete(project);
//    }
//
//    private void validateAndSetReferences(Project project) {
//        if (project.getProjectManager() != null) {
//            Optional<ProjectManager> pmOpt = projectManagerRepository.findById(project.getProjectManager().getId());
//            pmOpt.ifPresent(project::setProjectManager);
//        }
//        if (project.getClient() != null) {
//            Optional<Client> clientOpt = clientRepository.findById(project.getClient().getId());
//            clientOpt.ifPresent(project::setClient);
//        }
//    }
//
//    private ProjectDTO mapToDTO(Project project) {
//        List<TaskDTO> taskDetails = project.getTasks().stream()
//                .map(task -> new TaskDTO(task.getId(), task.getTitle()))
//                .collect(Collectors.toList());
//
//        return new ProjectDTO(
//                project.getId(),
//                project.getName(),
//                project.getDescription(),
//                project.getClient().getName(),
//                project.getProjectManager().getId(),
//                project.getProjectManager().getUsername(),
//                taskDetails
//        );
//    }
//
//
//    public Project createProject(Project project) {
//        validateAndSetReferences(project);
//        return projectRepository.save(project);
//    }
//
//    public Project updateProject(Long id, Project projectDetails) {
//        Optional<Project> projectOpt = projectRepository.findById(id);
//        if (projectOpt.isPresent()) {
//            Project existingProject = projectOpt.get();
//            existingProject.setName(projectDetails.getName());
//            existingProject.setDescription(projectDetails.getDescription());
//            existingProject.setProjectManager(projectDetails.getProjectManager());
//            existingProject.setClient(projectDetails.getClient());
//            existingProject.setTeamMembers(projectDetails.getTeamMembers());
//            existingProject.setTasks(projectDetails.getTasks());
//            validateAndSetReferences(existingProject);
//            return projectRepository.save(existingProject);
//        } else {
//            throw new RuntimeException("Project not found");
//        }
//    }
//
//}

// to check unit testing
//
//@Service
//public class ProjectService {
//    @Autowired
//    private ProjectRepository projectRepository;
//    @Autowired
//    private ProjectManagerRepository projectManagerRepository;
//    @Autowired
//    private ClientRepository clientRepository;
//
//    public ProjectDTO getProjectDTOById(Long id) {
//        Optional<Project> projectOpt = projectRepository.findById(id);
//        if (projectOpt.isPresent()) {
//            return mapToDTO(projectOpt.get());
//        } else {
//            throw new ResourceNotFoundException("Project not found with id: " + id);
//        }
//    }
//
//    public List<ProjectDTO> getAllProjects() {
//        List<Project> projects = projectRepository.findAll();
//        return projects.stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public void deleteProject(Long id) {
//        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//        projectRepository.delete(project);
//    }
//
//    private void validateAndSetReferences(Project project) {
//        if (project.getProjectManager() != null) {
//            Optional<ProjectManager> pmOpt = projectManagerRepository.findById(project.getProjectManager().getId());
//            pmOpt.ifPresent(project::setProjectManager);
//        }
//        if (project.getClient() != null) {
//            Optional<Client> clientOpt = clientRepository.findById(project.getClient().getId());
//            clientOpt.ifPresent(project::setClient);
//        }
//    }
//
//    private ProjectDTO mapToDTO(Project project) {
//        List<TaskDTO> taskDetails = (project.getTasks() != null ? project.getTasks() : Collections.emptyList())
//                .stream()
//                .filter(Objects::nonNull)  // Ensure task is not null
//                .map(task -> {
//                    Long taskId = task.getId() != null ? task.getId() : -1; // Handle null task ID
//                    String taskTitle = task.getTitle() != null ? task.getTitle() : "Untitled"; // Handle null task title
//                    return new TaskDTO(taskId, taskTitle);
//                })
//                .collect(Collectors.toList());
//
//        Long projectManagerId = project.getProjectManager() != null ? project.getProjectManager().getId() : null;
//        String projectManagerUsername = project.getProjectManager() != null ? project.getProjectManager().getUsername() : null;
//        String clientName = project.getClient() != null ? project.getClient().getName() : null;
//
//        return new ProjectDTO(
//                project.getId(),
//                project.getName(),
//                project.getDescription(),
//                clientName,
//                projectManagerId,
//                projectManagerUsername,
//                taskDetails
//        );
//    }
//
//
//    public Project createProject(Project project) {
//        validateAndSetReferences(project);
//        return projectRepository.save(project);
//    }
//
//    public Project updateProject(Long id, Project projectDetails) {
//        Optional<Project> projectOpt = projectRepository.findById(id);
//        if (projectOpt.isPresent()) {
//            Project existingProject = projectOpt.get();
//            existingProject.setName(projectDetails.getName());
//            existingProject.setDescription(projectDetails.getDescription());
//            existingProject.setProjectManager(projectDetails.getProjectManager());
//            existingProject.setClient(projectDetails.getClient());
//            existingProject.setTeamMembers(projectDetails.getTeamMembers());
//            existingProject.setTasks(projectDetails.getTasks());
//            validateAndSetReferences(existingProject);
//            return projectRepository.save(existingProject);
//        } else {
//            throw new RuntimeException("Project not found");
//        }
//    }
//}


