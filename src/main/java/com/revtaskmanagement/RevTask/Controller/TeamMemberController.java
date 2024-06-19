package com.revtaskmanagement.RevTask.Controller;

//import com.revtaskmanagement.RevTask.Entity.TeamMember;
//import com.revtaskmanagement.RevTask.Service.TeamMemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/team-members")
//public class TeamMemberController {
//
//    @Autowired
//    private TeamMemberService teamMemberService;
//
//    @GetMapping
//    public List<TeamMember> getAllTeamMembers() {
//        return teamMemberService.getAllTeamMembers();
//    }
//
//    @GetMapping("/{id}")
//    public TeamMember getTeamMemberById(@PathVariable Long id) {
//        return teamMemberService.getTeamMemberById(id);
//    }
//
//    @PostMapping
//    public TeamMember createTeamMember(@RequestBody TeamMember teamMember) {
//        return teamMemberService.createTeamMember(teamMember);
//    }
//
//    @PutMapping("/{id}")
//    public TeamMember updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMemberDetails) {
//        return teamMemberService.updateTeamMember(id, teamMemberDetails);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteTeamMember(@PathVariable Long id) {
//        teamMemberService.deleteTeamMember(id);
//    }
//}

import com.revtaskmanagement.RevTask.DTO.LoginRequestDTO;
import com.revtaskmanagement.RevTask.DTO.TeamMemberDTO;
import com.revtaskmanagement.RevTask.Entity.TeamMember;
import com.revtaskmanagement.RevTask.Service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/team-members")
public class    TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembers() {
        List<TeamMemberDTO> teamMembers = teamMemberService.getAllTeamMembers();

        if(teamMembers == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }else{
            return new ResponseEntity<>(teamMembers,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public TeamMemberDTO getTeamMemberById(@PathVariable Long id) {

        return teamMemberService.getTeamMemberById(id);
    }


    @PostMapping
    public ResponseEntity<TeamMember> createTeamMember(@RequestBody TeamMember teamMember) {

        TeamMember teamMember1  = teamMemberService.createTeamMember(teamMember);

        if(teamMember1 == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


//

    @PutMapping("/{id}")
    public TeamMemberDTO updateTeamMember(@PathVariable Long id, @RequestBody TeamMemberDTO teamMemberDTO) {
        return teamMemberService.updateTeamMemberEmailAndRole(id, teamMemberDTO);
    }


    @DeleteMapping("/{id}")
    public void deactivateTeamMember(@PathVariable Long id) {
        teamMemberService.deactivateTeamMember(id);
    }

    @PutMapping("/activate/{id}")
    public void activateTeamMember(@PathVariable Long id) {
        teamMemberService.activateTeamMember(id);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
//
//        boolean isValid = teamMemberService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
//        if (isValid) {
//            return ResponseEntity.ok("Login successful. Redirecting to home page...");
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        Long userId  = teamMemberService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();

        if (userId > 0) {
            response.put("message", "Login successful. Redirecting to home page...");
            response.put("userId", userId.toString());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
}


}
