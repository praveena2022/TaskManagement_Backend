package com.revtaskmanagement.RevTask.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class ProjectCustom1DTO {

        private Long id;
        private String name;
        private String description;
        private String clientName;
        private Long projectManagerId;
        private String projectManagerName;
        private List<TaskDTO> tasks;
        private List<TeamMemberDTO> teamMembers;

        public ProjectCustom1DTO(Long id, String name, String description, String clientName, Long projectManagerId, String projectManagerName, List<TaskDTO> tasks, List<TeamMemberDTO> teamMembers) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.clientName = clientName;
            this.projectManagerId = projectManagerId;
            this.projectManagerName = projectManagerName;
            this.tasks = tasks;
            this.teamMembers = teamMembers;
        }

        // Getters and setters
    }


