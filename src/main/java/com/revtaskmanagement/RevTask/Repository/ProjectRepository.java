package com.revtaskmanagement.RevTask.Repository;

import com.revtaskmanagement.RevTask.DTO.ProjectDTO;
import com.revtaskmanagement.RevTask.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.projectManager.id = :projectManagerId")
    List<Project> findAllByProjectManagerId(@Param("projectManagerId") Long projectManagerId);



    @Query("SELECT p FROM Project p JOIN p.teamMembers tm WHERE tm.id = :teamMemberId")
    List<Project> findAllByTeamMemberId(@Param("teamMemberId") Long teamMemberId);
}
