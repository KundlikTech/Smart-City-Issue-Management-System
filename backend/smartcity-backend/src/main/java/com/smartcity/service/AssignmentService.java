package com.smartcity.service;

import com.smartcity.model.Assignment;
import java.util.List;

public interface AssignmentService {
    Long assign(Long issueId, Long deptId, Long adminId);
    List<Assignment> getAll();
    List<Assignment> getByDepartment(Long deptId);
    void markCompleted(Long id);
}
