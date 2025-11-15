package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SlaIssueDTO {
    private Long issueId;
    private String title;
    private String departmentName;
    private Double hoursOverdue;      // how many hours overdue (can be fractional)
    private LocalDateTime reportedDate;
    private LocalDateTime assignedDate;
    private String assignedTo;        // email or name of assigned officer
}
