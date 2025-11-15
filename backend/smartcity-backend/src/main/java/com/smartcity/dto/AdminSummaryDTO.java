package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class AdminSummaryDTO {
    private long totalIssues;
    private long pending;
    private long inProgress;
    private long resolved;
    private long openAssignments;
}
