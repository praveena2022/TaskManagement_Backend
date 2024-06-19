package com.revtaskmanagement.RevTask.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamMemberCustomDTO {

        private Long id;
        private String username;
        private String email;
        private String role;

        public TeamMemberCustomDTO(Long id, String username, String email, String role) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.role = role;
        }
}
