package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentStatsDTO {

    private long pending;
    private long inProgress;
    private long resolved;
}
