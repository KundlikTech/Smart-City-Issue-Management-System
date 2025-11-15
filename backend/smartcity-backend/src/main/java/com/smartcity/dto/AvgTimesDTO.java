package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgTimesDTO {
    // Report -> Assignment average hours
    private Double avgResponseHours;
    // Report -> Resolved average hours
    private Double avgResolutionHours;
}
