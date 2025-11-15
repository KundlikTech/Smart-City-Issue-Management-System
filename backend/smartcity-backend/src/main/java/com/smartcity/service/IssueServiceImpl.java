package com.smartcity.service;

import com.smartcity.model.Issue;
import com.smartcity.model.User;
import com.smartcity.model.Department;
import com.smartcity.model.Assignment;
import com.smartcity.repository.IssueRepository;
import com.smartcity.repository.DepartmentRepository;
import com.smartcity.repository.AssignmentRepository;
import com.smartcity.util.DepartmentResolver;
import com.smartcity.dto.MapEvent;
import com.smartcity.dto.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Issue createIssue(Issue issue, User user) {
        issue.setUser(user);

        String deptName = DepartmentResolver.getDepartment(issue.getCategory());
        Department dept = departmentRepository.findByName(deptName)
                .orElseThrow(() -> new RuntimeException("Department not found: " + deptName));

        Issue savedIssue = issueRepository.save(issue);

        Assignment assign = new Assignment();
        assign.setIssue(savedIssue);
        assign.setDepartment(dept);
        assign.setAssignedBy(user);
        assign.setStatus("ASSIGNED");
        assign.setAssignedDate(LocalDateTime.now());

        assignmentRepository.save(assign);

        // create and broadcast notification / map event
        MapEvent m = new MapEvent();
        m.setType("ISSUE_CREATED");
        m.setIssueId(savedIssue.getId());
        m.setIssueTitle(savedIssue.getTitle());
        m.setLatitude(savedIssue.getLatitude());
        m.setLongitude(savedIssue.getLongitude());
        m.setTimestamp(LocalDateTime.now());
        notificationService.broadcastMapEvent(m);

        Notification n = new Notification();
        n.setType("ISSUE_CREATED");
        n.setIssueId(savedIssue.getId());
        n.setIssueTitle(savedIssue.getTitle());
        n.setTimestamp(LocalDateTime.now());
        notificationService.broadcastAssignment(n);

        return savedIssue;
    }

    @Override
    public Issue reportIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public List<Issue> getIssuesByUser(Long userId) {
        return issueRepository.findByUserId(userId);
    }

    @Override
    public List<Issue> getIssuesByStatus(String status) {
        return issueRepository.findByStatus(status);
    }

    @Override
    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    @Override
    public Issue updateIssueStatus(Long id, String status) {
        Optional<Issue> existing = issueRepository.findById(id);
        if (existing.isPresent()) {
            Issue issue = existing.get();
            String oldStatus = issue.getStatus();
            String newStatus = status.toUpperCase(Locale.ROOT);
            issue.setStatus(newStatus);
            if ("RESOLVED".equalsIgnoreCase(newStatus)) {
                issue.setResolvedDate(LocalDateTime.now());
            }
            Issue saved = issueRepository.save(issue);

            // notifications & map events
            Notification n = new Notification();
            n.setType("ISSUE_STATUS_UPDATED");
            n.setIssueId(saved.getId());
            n.setIssueTitle(saved.getTitle());
            n.setOldStatus(oldStatus);
            n.setNewStatus(newStatus);
            n.setTimestamp(LocalDateTime.now());
            notificationService.broadcastStatusUpdate(n);

            MapEvent statusEvent = new MapEvent();
            statusEvent.setType("ISSUE_STATUS_UPDATED_MAP");
            statusEvent.setIssueId(saved.getId());
            statusEvent.setIssueTitle(saved.getTitle());
            statusEvent.setIssueStatus(saved.getStatus());
            statusEvent.setLatitude(saved.getLatitude());
            statusEvent.setLongitude(saved.getLongitude());
            statusEvent.setActor("SYSTEM");
            statusEvent.setTimestamp(LocalDateTime.now());
            notificationService.broadcastMapEvent(statusEvent);

            return saved;
        }
        return null;
    }

    @Override
    public Map<String, Long> getIssueStats() {
        long total = issueRepository.count();
        long pending = issueRepository.countByStatus("PENDING");
        long inProgress = issueRepository.countByStatus("IN_PROGRESS");
        long resolved = issueRepository.countByStatus("RESOLVED");

        Map<String, Long> stats = new HashMap<>();
        stats.put("totalIssues", total);
        stats.put("pending", pending);
        stats.put("inProgress", inProgress);
        stats.put("resolved", resolved);
        return stats;
    }

    @Override
    public List<Map<String, Object>> getLast7DaysTrend() {
        List<Object[]> rows = issueRepository.countLast7Days();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", row[0] != null ? row[0].toString() : null);
            map.put("count", row[1] != null ? ((Number) row[1]).intValue() : 0);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getLast6MonthsTrend() {
        List<Object[]> rows = issueRepository.countLast6Months();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", row[0] != null ? row[0].toString() : null);
            map.put("count", row[1] != null ? ((Number) row[1]).intValue() : 0);
            result.add(map);
        }
        return result;
    }
}
