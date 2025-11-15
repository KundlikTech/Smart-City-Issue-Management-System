package com.smartcity.controller;

import com.smartcity.model.Issue;
import com.smartcity.model.User;
import com.smartcity.service.IssueService;
import com.smartcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling city issues reported by citizens.
 */
@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    /**
     * ✅ Report a new issue
     * Endpoint: POST /api/issues
     */
    @PostMapping
    public ResponseEntity<Issue> reportIssue(@RequestBody Issue issue, @RequestParam Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        issue.setUser(user.get());
        Issue savedIssue = issueService.reportIssue(issue);
        return new ResponseEntity<>(savedIssue, HttpStatus.CREATED);
    }

    /**
     * ✅ Get all issues (for admin view)
     * Endpoint: GET /api/issues
     */
    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    /**
     * ✅ Get all issues by user ID
     * Endpoint: GET /api/issues/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Issue>> getIssuesByUser(@PathVariable Long userId) {
        List<Issue> issues = issueService.getIssuesByUser(userId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    /**
     * ✅ Get all issues by status
     * Endpoint: GET /api/issues/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Issue>> getIssuesByStatus(@PathVariable String status) {
        List<Issue> issues = issueService.getIssuesByStatus(status);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    /**
     * ✅ Get issue by ID
     * Endpoint: GET /api/issues/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Optional<Issue> issue = issueService.getIssueById(id);
        return issue.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * ✅ Update issue status (Admin can change)
     * Endpoint: PUT /api/issues/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long id, @RequestParam String status) {
        Issue updated = issueService.updateIssueStatus(id, status);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
