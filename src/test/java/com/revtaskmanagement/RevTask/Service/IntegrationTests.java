//package com.revtaskmanagement.RevTask.Service;
//
//import com.revtaskmanagement.RevTask.DTO.*;
//import com.revtaskmanagement.RevTask.Entity.*;
//import com.revtaskmanagement.RevTask.Repository.*;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//public class IntegrationTests {
//
//    @Autowired
//    private TeamMemberService teamMemberService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private ClientService clientService;
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private TeamMemberRepository teamMemberRepository;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private ProjectManagerRepository projectManagerRepository;
//
////    @BeforeEach
////    public void setUp() {
////        teamMemberRepository.deleteAll();
////        projectRepository.deleteAll();
////        taskRepository.deleteAll();
////        clientRepository.deleteAll();
////        adminRepository.deleteAll();
////    }
//
//    // TeamMemberService Tests
//    @Test
//    public void testCreateAndGetTeamMember() {
//        TeamMember teamMember = new TeamMember();
//        teamMember.setEmail("team@example.com");
//        teamMember.setRole("Developer");
//        teamMember.setActive(true);
//
//        TeamMember createdTeamMember = teamMemberService.createTeamMember(teamMember);
//        TeamMemberDTO fetchedTeamMember = teamMemberService.getTeamMemberById(createdTeamMember.getId());
//
//        assertEquals("team@example.com", fetchedTeamMember.getEmail());
//        assertEquals("Developer", fetchedTeamMember.getRole());
//    }
//
//    @Test
//    public void testDeactivateAndActivateTeamMember() {
//        TeamMember teamMember = new TeamMember();
//        teamMember.setEmail("team@example.com");
//        teamMember.setRole("Developer");
//        teamMember.setActive(true);
//
//        TeamMember createdTeamMember = teamMemberRepository.save(teamMember);
//
//        teamMemberService.deactivateTeamMember(createdTeamMember.getId());
//        assertFalse(teamMemberRepository.findById(createdTeamMember.getId()).get().isActive());
//
//        teamMemberService.activateTeamMember(createdTeamMember.getId());
//        assertTrue(teamMemberRepository.findById(createdTeamMember.getId()).get().isActive());
//    }
//
//    // ProjectService Tests
//    @Test
//    public void testCreateAndGetProject() {
//        ProjectManager projectManager = new ProjectManager();
//        Client client = new Client();
//
//        when(projectManagerRepository.findById(1L)).thenReturn(Optional.of(projectManager));
//        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
//        when(projectRepository.save(any(Project.class))).thenReturn(new Project());
//
//        ProjectCustom2DTO projectDTO = new ProjectCustom2DTO();
//        projectDTO.setName("New Project");
//        projectDTO.setDescription("Description");
//        projectDTO.setClientId(1L);
//        projectDTO.setDueDate(new Date());
//        projectDTO.setAssignedDate(new Date());
//
//        projectService.createProject(1L, projectDTO);
////        ProjectManager projectManager = new ProjectManager();
////        projectManager.setUsername("manager1");
////        projectManagerRepository.save(projectManager);
////        Client client = new Client();
////        client.setName("Client1");
////        clientRepository.save(client);
////        ProjectCustom2DTO projectDTO = new ProjectCustom2DTO();
////        projectDTO.setName("New Project");
////        projectDTO.setDescription("Description");
////        projectDTO.setClientId(client.getId());
////        projectDTO.setDueDate(new Date());
////        projectDTO.setAssignedDate(new Date());
////
////        projectService.createProject(projectManager.getId(), projectDTO);
////
////        Optional<Project> createdProjectOpt = projectRepository.findAll().stream().findFirst();
////        assertThat(createdProjectOpt).isPresent();
////
////        Project createdProject = createdProjectOpt.get();
////        assertThat(createdProject.getName()).isEqualTo("New Project");
////        assertThat(createdProject.getDescription()).isEqualTo("Description");
////        assertThat(createdProject.getClient().getName()).isEqualTo("Client1");
////        assertThat(createdProject.getProjectManager().getUsername()).isEqualTo("manager1");
//        ProjectDTO project1 = new ProjectDTO();
//        project1.setId(1L);
//        project1.setName("Project A");
//        project1.setDescription("Description A");
//        project1.setClientName("Client1");
//        project1.setProjectManagerId(1L);
//        project1.setProjectManagerName("pm1");
//        project1.setTasks(Collections.emptyList());
//
//        ProjectDTO project2 = new ProjectDTO();
//        project2.setId(2L);
//        project2.setName("Project B");
//        project2.setDescription("Description B");
//        project2.setClientName("Client2");
//        project2.setProjectManagerId(1L);
//        project2.setProjectManagerName("pm2");
//        project2.setTasks(Collections.emptyList());
//
//
//        List<ProjectDTO> projects = Arrays.asList(project1, project2);
//
////        Mockito.when(projectRepository.findAll()).thenReturn(projects);
////        Mockito.when(projectRepository.findAll()).thenReturn(projects);
//        Mockito.when(projectRepository.findAll())
//                .thenReturn(projects.stream()
//                        .map(this::convertToProjectEntity)
//                        .collect(Collectors.toList()));
//        List<ProjectDTO> projectDTOs = projectService.getAllProjects();
//
//        Assertions.assertNotNull(projectDTOs);
//        Assertions.assertEquals(2, projectDTOs.size());
//    }
//    private Project convertToProjectEntity(ProjectDTO dto) {
//        Project project = new Project();
//        project.setId(dto.getId());
//        project.setName(dto.getName());
//        project.setDescription(dto.getDescription());
//        // Set other fields as needed
//        if (dto.getClientName() != null) {
//            Client client = new Client();
//            client.setName(dto.getClientName());
//            project.setClient(client);
//        }
//        if (dto.getProjectManagerId() != null) {
//            ProjectManager projectManager = new ProjectManager();
////            client.setName(dto.getClientName());
//            projectManager.setId(dto.getProjectManagerId());
//            project.setProjectManager(projectManager);
//        }
//
//
//        project.setTasks(Collections.emptyList());
//        return project;
//    }
//
//
//
//    @Test
//    public void testUpdateProject() {
//
//        ProjectManager projectManager = new ProjectManager();
//        projectManager.setUsername("manager1");
//        projectManagerRepository.save(projectManager);
//
//        Client client = new Client();
//        client.setName("Client1");
//        clientRepository.save(client);
//
//        Project project = new Project();
//        project.setName("Initial Project");
//        project.setDescription("Initial Description");
//        project.setClient(client);
//        project.setProjectManager(projectManager);
//        project.setDueDate(new Date());
//        project.setAssignedDate(new Date());
//
//        projectRepository.save(project);
//
//        Project projectDTO = new Project();
//        projectDTO.setName("Updated Project");
//        projectDTO.setDescription("Updated Description");
//      //  projectDTO.setClientId(client.getId());
//        projectDTO.setDueDate(new Date());
//        projectDTO.setAssignedDate(new Date());
//
//        projectService.updateProject(project.getId(), projectDTO);
//
//        Optional<Project> updatedProjectOpt = projectRepository.findById(project.getId());
//        assertThat(updatedProjectOpt).isPresent();
//
//        Project updatedProject = updatedProjectOpt.get();
//        assertThat(updatedProject.getName()).isEqualTo("Updated Project");
//        assertThat(updatedProject.getDescription()).isEqualTo("Updated Description");
//        assertThat(updatedProject.getClient().getName()).isEqualTo("Client1");
//        assertThat(updatedProject.getProjectManager().getUsername()).isEqualTo("manager1");
//        assertThat(updatedProject.getDueDate()).isEqualTo(LocalDate.now().plusDays(60));
//        assertThat(updatedProject.getAssignedDate()).isEqualTo(LocalDate.now().plusDays(10));
//    }
//
//    // TaskService Tests
//    @Test
//    public void testCreateAndGetTask() {
//        Task task = new Task();
//        task.setTitle("Task A");
//        task.setDescription("Description A");
//
//        Task createdTask = taskService.createTask(task);
//        TaskDetailDTO fetchedTask = taskService.getTaskById(createdTask.getId());
//
//        assertEquals("Task A", fetchedTask.getTitle());
//        assertEquals("Description A", fetchedTask.getDescription());
//    }
//
//    @Test
//    public void testUpdateTask() {
//        Task task = new Task();
//        task.setTitle("Task A");
//        task.setDescription("Description A");
//
//        Task createdTask = taskService.createTask(task);
//        createdTask.setTitle("Task B");
//        createdTask.setDescription("Description B");
//
//        Task updatedTask = taskService.updateTask(createdTask.getId(), createdTask);
//        assertEquals("Task B", updatedTask.getTitle());
//        assertEquals("Description B", updatedTask.getDescription());
//    }
//
//    // ClientService Tests
//    @Test
//    public void testCreateAndGetClient() {
//        Client client = new Client();
//        client.setName("Client A");
//        client.setEmail("client@example.com");
//
//        Client createdClient = clientRepository.save(client);
//        ClientDTO fetchedClient = clientService.getClientWithProjectsById(createdClient.getId());
//
//        assertEquals("Client A", fetchedClient.getName());
//        assertEquals("client@example.com", fetchedClient.getEmail());
//    }
//
//    // AdminService Tests
//    @Test
//    public void testCreateAndGetAdmin() {
//        Admin admin = new Admin();
//        admin.setUsername("admin");
//        admin.setPassword("password");
//        admin.setEmail("admin@example.com");
//
//        Admin createdAdmin = adminService.createAdmin(admin);
//        Admin fetchedAdmin = adminService.getAdminById(createdAdmin.getId());
//
//        assertEquals("admin", fetchedAdmin.getUsername());
//        assertEquals("admin@example.com", fetchedAdmin.getEmail());
//    }
//
//    @Test
//    public void testDeleteAdmin() {
//        Admin admin = new Admin();
//        admin.setUsername("admin");
//        admin.setPassword("password");
//        admin.setEmail("admin@example.com");
//
//        Admin createdAdmin = adminService.createAdmin(admin);
//        adminService.deleteAdmin(createdAdmin.getId());
//
//        assertThrows(EntityNotFoundException.class, () -> adminService.getAdminById(createdAdmin.getId()));
//    }
//}
//
