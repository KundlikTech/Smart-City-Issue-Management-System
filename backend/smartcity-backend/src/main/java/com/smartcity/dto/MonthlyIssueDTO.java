package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class MonthlyIssueDTO {
    private String month;
    private long count;
}
