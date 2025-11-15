package com.smartcity.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class TopDeptDTO {
    private Long departmentId;
    private String departmentName;
    private long count;
}
