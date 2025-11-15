package com.smartcity.controller;

import com.smartcity.model.Assignment;
import com.smartcity.service.AdminService;
import com.smartcity.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired 
    private AdminService adminService;

    // Create assignment
    @PostMapping
    public ResponseEntity<Long> assignIssue(
            @RequestParam Long issueId,
            @RequestParam Long departmentId,
            @RequestParam Long adminId
    ) {
        Long id = assignmentService.assign(issueId, departmentId, adminId);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAll());
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<List<Assignment>> getByDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getByDepartment(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> completeAssignment(@PathVariable Long id) {
        assignmentService.markCompleted(id);
        return ResponseEntity.ok("Assignment Completed");
    }

    @GetMapping("/assignments/active")
public ResponseEntity<List<Assignment>> activeAssignments() {
    return ResponseEntity.ok(adminService.getActiveAssignments());
}

}
