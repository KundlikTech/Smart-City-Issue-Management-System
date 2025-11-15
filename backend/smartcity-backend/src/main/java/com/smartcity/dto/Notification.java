package com.smartcity.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Notification {

    private String type;  // ISSUE_STATUS_UPDATED, ASSIGNMENT_CREATED

    private Long issueId;
    private String issueTitle;

    private String oldStatus;
    private String newStatus;
    private String updatedBy;

    private Long assignmentId;
    private String departmentName;

    private String assignedBy;   // << FIX HERE

    private LocalDateTime timestamp;

    
}
