package com.revtaskmanagement.RevTask.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
public class Project {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private Date assignedDate;

        @Column(nullable = false)
        private Date dueDate;

        @ManyToOne
        @JoinColumn(name = "project_manager_id", nullable = false)
        private ProjectManager projectManager;

        @ManyToMany
        @JoinTable(
                name = "project_team_member",
                joinColumns = @JoinColumn(name = "project_id", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "team_member_id",nullable = false)
        )
        private List<TeamMember> teamMembers;

        @OneToMany(mappedBy = "project")
        private List<Task> tasks;

        @ManyToOne
        @JoinColumn(name = "client_id", nullable = false)
        private Client client;

        @OneToOne(mappedBy = "project")
        private ProjectDetail projectDetail;

        public String getClientName(Project project) {
                Client client = project.getClient();
                if (client != null) {
                        return client.getName();
                } else {
                        // Handle the case where the Client object is null
                        return "Unknown Client";
                }
        }

        // Added one


//        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<Task> tasks;
//
//        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<TeamMember> teamMembers;
//
//        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<ProjectManager> projectManagers;
//
//        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<Client> clients;

}
