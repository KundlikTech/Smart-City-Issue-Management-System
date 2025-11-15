package com.smartcity.service;

import com.smartcity.dto.DepartmentStatsDTO;
import com.smartcity.dto.MonthlyIssueDTO;
import com.smartcity.model.Assignment;
import com.smartcity.model.Department;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing city departments.
 */
public interface DepartmentService {

    /**
     * Create or update a department.
     * @param department the department to save
     * @return saved department
     */
    Department saveDepartment(Department department);

    /**
     * Get all departments.
     * @return list of departments
     */
    List<Department> getAllDepartments();

    /**
     * Get a department by ID.
     * @param id department id
     * @return optional department
     */
    Optional<Department> getDepartmentById(Long id);

    /**
     * Delete a department by ID.
     * @param id department id
     */
    void deleteDepartment(Long id);

    /**
     * Find a department by name.
     * @param name department name
     * @return optional department
     */
    Optional<Department> findByName(String name);

    DepartmentStatsDTO getDepartmentStats(Long deptId);

    List<MonthlyIssueDTO> getDepartmentMonthlyStats(Long deptId);

    Double getAvgResolutionTime(Long deptId);

    List<Assignment> getAssignments(Long deptId);
}
