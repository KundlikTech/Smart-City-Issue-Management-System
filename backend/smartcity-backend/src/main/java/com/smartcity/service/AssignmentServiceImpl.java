package com.smartcity.service;

import com.smartcity.dto.MapEvent;
import com.smartcity.dto.Notification;
import com.smartcity.model.*;
import com.smartcity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NotificationService notificationService;

      @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public Long assign(Long issueId, Long deptId, Long adminId) {

        Issue issue = issueRepo.findById(issueId).orElseThrow();
        Department dept = deptRepo.findById(deptId).orElseThrow();
        User admin = userRepo.findById(adminId).orElseThrow();

        Assignment a = new Assignment();
        a.setIssue(issue);
        a.setDepartment(dept);
        a.setAssignedBy(admin);
        a.setStatus("ASSIGNED");

        Assignment saved = assignmentRepo.save(a);
        return saved.getId();
    }

    @Override
    public List<Assignment> getAll() {
        return assignmentRepo.findAll();
    }

    @Override
    public List<Assignment> getByDepartment(Long deptId) {
        return assignmentRepo.findByDepartmentId(deptId);
    }

    @Override
    public void markCompleted(Long id) {
        Assignment a = assignmentRepo.findById(id).orElseThrow();
        a.setStatus("COMPLETED");
        a.setCompletedDate(LocalDateTime.now());
        assignmentRepo.save(a);
    }

    public Assignment saveAssignment(Assignment assignment) {
        // ensure assignedDate etc. are set if needed
        assignment.setAssignedDate(LocalDateTime.now());
        Assignment saved = assignmentRepository.save(assignment);

        MapEvent assignEvent = new MapEvent();
assignEvent.setType("ISSUE_ASSIGNED");
assignEvent.setIssueId(saved.getIssue().getId());
assignEvent.setIssueTitle(saved.getIssue().getTitle());
assignEvent.setLatitude(saved.getIssue().getLatitude());
assignEvent.setLongitude(saved.getIssue().getLongitude());
assignEvent.setExtra("Assigned to " + saved.getDepartment().getName());
assignEvent.setActor(saved.getAssignedBy() != null ? saved.getAssignedBy().getEmail() : "SYSTEM");
assignEvent.setTimestamp(LocalDateTime.now());

notificationService.broadcastMapEvent(assignEvent);

        // build notification
        Notification n = new Notification();
        n.setType("ASSIGNMENT_CREATED");
        n.setAssignmentId(saved.getId());
        Issue issue = saved.getIssue();
        if (issue != null) {
            n.setIssueId(issue.getId());
            n.setIssueTitle(issue.getTitle());
        }
        Department dept = saved.getDepartment();
        if (dept != null) n.setDepartmentName(dept.getName());
        User by = saved.getAssignedBy();
        if (by != null) n.setAssignedBy(by.getEmail() != null ? by.getEmail() : by.getName());
        n.setTimestamp(LocalDateTime.now());

        // broadcast to everyone
        notificationService.broadcastAssignment(n);

        // optionally send to department users or specific user:
        // notificationService.sendToUser("admin@smartcity.gov.in", n);

        return saved;
    }
}
