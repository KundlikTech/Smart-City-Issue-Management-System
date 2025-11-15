package com.smartcity.service;

import com.smartcity.dto.*;
import com.smartcity.model.Assignment;
import com.smartcity.model.Issue;
import com.smartcity.repository.AssignmentRepository;
import com.smartcity.repository.IssueRepository;
import com.smartcity.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final IssueRepository issueRepository;
    private final AssignmentRepository assignmentRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public AdminServiceImpl(IssueRepository issueRepository,
                            AssignmentRepository assignmentRepository,
                            DepartmentRepository departmentRepository) {
        this.issueRepository = issueRepository;
        this.assignmentRepository = assignmentRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public AdminSummaryDTO getSummary() {
        long total = issueRepository.count();
        long pending = issueRepository.countByStatus("PENDING");
        long inProgress = issueRepository.countByStatus("IN_PROGRESS");
        long resolved = issueRepository.countByStatus("RESOLVED");
        long openAssignments = assignmentRepository.findAll()
                .stream().filter(a -> !"COMPLETED".equalsIgnoreCase(a.getStatus())).count();

        return new AdminSummaryDTO(total, pending, inProgress, resolved, openAssignments);
    }

    @Override
    public Page<Issue> getRecentIssues(String status, Long deptId, Pageable pr) {
        if (status != null && !status.isBlank()) {
            return issueRepository.findAllByStatus(status.toUpperCase(), pr);
        }
        return issueRepository.findAll(pr);
    }

    @Override
    public List<TopDeptDTO> getTopDepartments(int days, int limit) {
        // naive implementation: count assignments per department in last 'days'
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        List<Assignment> assigns = assignmentRepository.findAll(); // optimize later
        Map<Long, Long> counts = new HashMap<>();
        for (Assignment a : assigns) {
            if (a.getAssignedDate() != null && a.getAssignedDate().isAfter(from)) {
                counts.merge(a.getDepartment().getId(), 1L, Long::sum);
            }
        }
        return counts.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Long deptId = e.getKey();
                    String name = departmentRepository.findById(deptId).map(d -> d.getName()).orElse("Unknown");
                    return new TopDeptDTO(deptId, name, e.getValue());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyIssueDTO> getMonthlyTrend(int months) {
        List<Object[]> rows = issueRepository.countLast6Months();
        List<MonthlyIssueDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add(new MonthlyIssueDTO((String) r[0], ((Number) r[1]).longValue()));
        }
        return out;
    }

    @Override
    public List<SlaIssueDTO> getSlaBreaches(long slaHours) {
        // naive implementation: iterate issues, compute difference between reported and assigned or now
        List<Issue> all = issueRepository.findAll();
        List<SlaIssueDTO> out = new ArrayList<>();
        for (Issue i : all) {
            if (i.getReportedDate() == null) continue;
            LocalDateTime assigned = null;
            // try to find assignment time — you may need to query assignment repository for issue
            // Here we skip if not assigned
            // (Better: assignmentRepository.findByIssueId(i.getId()) and use assignedDate)
        }
        // For now return empty list (or implement using assignmentRepository)
        return out;
    }

    @Override
    public AvgTimesDTO getAvgTimes() {
        // compute averages (report->assign) and (report->resolved)
        // We'll do simple approximate calculations using assignments/resolutions
        List<Issue> all = issueRepository.findAll();
        double totalRespHours = 0;
        double totalResHours = 0;
        int respCount = 0;
        int resCount = 0;

        for (Issue i : all) {
            if (i.getReportedDate() == null) continue;
            // find assignment date from assignmentRepository (not implemented here)
            // if you have assignmentRepository.findByIssueId(...), use it
            // For now, if issue has resolvedDate compute resolved hours
            if (i.getResolvedDate() != null) {
                long hours = ChronoUnit.HOURS.between(i.getReportedDate(), i.getResolvedDate());
                totalResHours += hours;
                resCount++;
            }
        }

        double avgResp = respCount == 0 ? 0.0 : totalRespHours / respCount;
        double avgRes = resCount == 0 ? 0.0 : totalResHours / resCount;
        return new AvgTimesDTO(avgResp, avgRes);
    }

    @Override
    public List<Assignment> getActiveAssignments() {
        return assignmentRepository.findAll()
                .stream().filter(a -> !"COMPLETED".equalsIgnoreCase(a.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] exportCsv(String type, int months) throws Exception {
        // simplistic CSV export — implement actual CSV content
        String header = "id,title,category,reportedDate,status\n";
        StringBuilder sb = new StringBuilder(header);
        List<Issue> issues = issueRepository.findAll();
        for (Issue i : issues) {
            sb.append(i.getId()).append(",");
            sb.append("\"").append(i.getTitle().replace("\"","'")).append("\",");
            sb.append(i.getCategory()).append(",");
            sb.append(i.getReportedDate()).append(",");
            sb.append(i.getStatus()).append("\n");
        }
        return sb.toString().getBytes();
    }
}
