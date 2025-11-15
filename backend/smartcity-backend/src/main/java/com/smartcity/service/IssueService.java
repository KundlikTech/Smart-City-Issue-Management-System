package com.smartcity.service;

import com.smartcity.model.Issue;
import com.smartcity.model.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IssueService {

    Issue reportIssue(Issue issue);

    List<Issue> getAllIssues();

    List<Issue> getIssuesByUser(Long userId);

    List<Issue> getIssuesByStatus(String status);

    Optional<Issue> getIssueById(Long id);

    Issue updateIssueStatus(Long id, String status);

    Issue createIssue(Issue issue, User user);

    Map<String, Long> getIssueStats();

    List<Map<String, Object>> getLast7DaysTrend();

    List<Map<String, Object>> getLast6MonthsTrend();
}
