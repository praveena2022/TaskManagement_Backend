package com.revtaskmanagement.RevTask.DTO;

import com.revtaskmanagement.RevTask.Entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCustom2DTO {

    private Long id;
    private String name;
    private String description;
    private Long clientId;
    private Date assignedDate;
    private Date dueDate;


}
