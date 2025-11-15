package com.smartcity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "assignments")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    @JsonIgnoreProperties({"user"})   // prevents Issue → User lazy loading
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    @JsonIgnoreProperties({"password"})  // avoid exposing password
    private User assignedBy;

    private String status = "ASSIGNED";
    private LocalDateTime assignedDate = LocalDateTime.now();
    private LocalDateTime completedDate;
}
