package com.smartcity.service;

import com.smartcity.dto.*;
import com.smartcity.model.Assignment;
import com.smartcity.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    AdminSummaryDTO getSummary();
    Page<Issue> getRecentIssues(String status, Long deptId, Pageable pr);
    List<TopDeptDTO> getTopDepartments(int days, int limit);
    List<MonthlyIssueDTO> getMonthlyTrend(int months);
    List<SlaIssueDTO> getSlaBreaches(long slaHours);
    AvgTimesDTO getAvgTimes();
    List<Assignment> getActiveAssignments();
    byte[] exportCsv(String type, int months) throws Exception;
}
