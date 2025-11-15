package com.smartcity.controller;

import com.smartcity.dto.*;
import com.smartcity.model.Assignment;
import com.smartcity.service.AdminService;
import com.smartcity.service.IssueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "${spring.web.cors.allowed-origins:http://localhost:3000}")
public class AdminController {

    private final AdminService adminService;
    private final IssueService issueService;

    @Autowired
    public AdminController(AdminService adminService, IssueService issueService) {
        this.adminService = adminService;
        this.issueService = issueService;
    }

    @GetMapping("/summary")
    public ResponseEntity<AdminSummaryDTO> summary() {
        return ResponseEntity.ok(adminService.getSummary());
    }

    @GetMapping("/recent-issues")
    public ResponseEntity<Page<?>> recentIssues(@RequestParam(defaultValue="0") int page,
                                                @RequestParam(defaultValue="20") int size,
                                                @RequestParam(required=false) String status,
                                                @RequestParam(required=false) Long deptId) {
        PageRequest pr = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "reportedDate"));
        return ResponseEntity.ok(adminService.getRecentIssues(status, deptId, pr));
    }

    @GetMapping("/top-departments")
    public ResponseEntity<List<TopDeptDTO>> topDepartments(@RequestParam(defaultValue="30") int days,
                                                           @RequestParam(defaultValue="5") int limit) {
        return ResponseEntity.ok(adminService.getTopDepartments(days, limit));
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<List<MonthlyIssueDTO>> monthlyTrend(@RequestParam(defaultValue="6") int months) {
        return ResponseEntity.ok(adminService.getMonthlyTrend(months));
    }

    @GetMapping("/sla-breaches")
    public ResponseEntity<List<SlaIssueDTO>> slaBreaches(@RequestParam(defaultValue="48") long slaHours) {
        return ResponseEntity.ok(adminService.getSlaBreaches(slaHours));
    }

    @GetMapping("/avg-times")
    public ResponseEntity<AvgTimesDTO> avgTimes() {
        return ResponseEntity.ok(adminService.getAvgTimes());
    }

    @GetMapping("/assignments/active")
    public ResponseEntity<List<Assignment>> activeAssignments() {
        return ResponseEntity.ok(adminService.getActiveAssignments());
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam(defaultValue="monthly") String type,
                                         @RequestParam(defaultValue="6") int months) throws Exception {
        byte[] csv = adminService.exportCsv(type, months);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"");
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }

    /**
     * New endpoint expected by frontend: /api/admin/dashboard-stats
     * Returns a combined payload for admin dashboard (summary + small trends)
     */
    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> dashboardStats() {
        Map<String, Object> out = new HashMap<>();
        out.put("summary", adminService.getSummary());
        out.put("weeklyTrend", issueService.getLast7DaysTrend());
        out.put("monthlyTrend", adminService.getMonthlyTrend(6));
        out.put("avgTimes", adminService.getAvgTimes());
        out.put("topDepartments", adminService.getTopDepartments(30, 5));
        return ResponseEntity.ok(out);
    }

    // Backwards-compatible endpoints used earlier in codebase:
    @GetMapping("/trend/week")
    public ResponseEntity<List<Map<String,Object>>> trendWeek() {
        return ResponseEntity.ok(issueService.getLast7DaysTrend());
    }
}
