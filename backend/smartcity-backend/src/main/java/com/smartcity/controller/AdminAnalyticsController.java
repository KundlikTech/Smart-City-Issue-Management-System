package com.smartcity.controller;

import com.smartcity.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminAnalyticsController {

    @Autowired
    private IssueService issueService;

    /**
     * Admin Analytics - Total Issues Stats
     * GET /api/admin/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(issueService.getIssueStats());
    }

    @GetMapping("/trend/daily")
public ResponseEntity<List<Map<String, Object>>> getDailyTrend() {
    return ResponseEntity.ok(issueService.getLast7DaysTrend());
}

@GetMapping("/trend/monthly")
public ResponseEntity<List<Map<String, Object>>> getMonthlyTrend() {
    return ResponseEntity.ok(issueService.getLast6MonthsTrend());
}

}
