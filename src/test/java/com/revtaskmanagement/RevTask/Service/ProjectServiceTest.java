package com.revtaskmanagement.RevTask.Service;

import com.revtaskmanagement.RevTask.Entity.*;
import com.revtaskmanagement.RevTask.DTO.*;
import com.revtaskmanagement.RevTask.Repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectManagerRepository projectManagerRepository;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ProjectService projectService;

    @Test
    public void testGetProjectDTOById() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setDescription("Description A");
        project.setTasks(Collections.emptyList());

        ProjectManager projectManager = new ProjectManager();
        projectManager.setId(1L);
        projectManager.setUsername("Manager A");

        Client client = new Client();
        client.setId(1L);
        client.setName("Client A");

        project.setProjectManager(projectManager);
        project.setClient(client);

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectDTO projectDTO = projectService.getProjectDTOById(1L);

        Assertions.assertNotNull(projectDTO);
        Assertions.assertEquals("Project A", projectDTO.getName());
    }

    @Test
    public void testGetAllProjects() {
        ProjectDTO project1 = new ProjectDTO();
        project1.setId(1L);
        project1.setName("Project A");
        project1.setDescription("Description A");
        project1.setClientName("Client1");
        project1.setProjectManagerId(1L);
        project1.setProjectManagerName("pm1");
        project1.setTasks(Collections.emptyList());

        ProjectDTO project2 = new ProjectDTO();
        project2.setId(2L);
        project2.setName("Project B");
        project2.setDescription("Description B");
        project2.setClientName("Client2");
        project2.setProjectManagerId(1L);
        project2.setProjectManagerName("pm2");
        project2.setTasks(Collections.emptyList());


        List<ProjectDTO> projects = Arrays.asList(project1, project2);

//        Mockito.when(projectRepository.findAll()).thenReturn(projects);
//        Mockito.when(projectRepository.findAll()).thenReturn(projects);
        Mockito.when(projectRepository.findAll())
                .thenReturn(projects.stream()
                        .map(this::convertToProjectEntity)
                        .collect(Collectors.toList()));
        List<ProjectDTO> projectDTOs = projectService.getAllProjects();

        Assertions.assertNotNull(projectDTOs);
        Assertions.assertEquals(2, projectDTOs.size());
    }
    private Project convertToProjectEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        // Set other fields as needed
        if (dto.getClientName() != null) {
            Client client = new Client();
            client.setName(dto.getClientName());
            project.setClient(client);
        }
        if (dto.getProjectManagerId() != null) {
            ProjectManager projectManager = new ProjectManager();
//            client.setName(dto.getClientName());
            projectManager.setId(dto.getProjectManagerId());
            project.setProjectManager(projectManager);
        }


        project.setTasks(Collections.emptyList());
        return project;
    }

    @Test
    public void testDeleteProject() {
        Project project = new Project();
        project.setId(1L);

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.deleteProject(1L);

        Mockito.verify(projectRepository, Mockito.times(1)).delete(project);
    }

    @Test
    public void testCreateProject() {


        ProjectManager projectManager = new ProjectManager();
        Client client = new Client();

        when(projectManagerRepository.findById(1L)).thenReturn(Optional.of(projectManager));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(projectRepository.save(any(Project.class))).thenReturn(new Project());

        ProjectCustom2DTO projectDTO = new ProjectCustom2DTO();
        projectDTO.setName("New Project");
        projectDTO.setDescription("Description");
        projectDTO.setClientId(1L);
        projectDTO.setDueDate(new Date());
        projectDTO.setAssignedDate(new Date());

        projectService.createProject(1L, projectDTO);
    }

    @Test
    public void testUpdateProject() {
        Project existingProject = new Project();
        existingProject.setId(1L);
        existingProject.setName("Project A");
        existingProject.setDescription("Description A");
        existingProject.setTasks(Collections.emptyList());

        Project updatedProjectDetails = new Project();
        updatedProjectDetails.setName("Updated Project A");
        updatedProjectDetails.setDescription("Updated Description A");
        updatedProjectDetails.setTasks(Collections.emptyList());

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(existingProject);

        Project updatedProject = projectService.updateProject(1L, updatedProjectDetails);

        Assertions.assertNotNull(updatedProject);
        Assertions.assertEquals("Updated Project A", updatedProject.getName());
    }
}
