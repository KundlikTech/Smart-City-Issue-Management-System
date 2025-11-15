package com.smartcity.service;

import com.smartcity.dto.DepartmentStatsDTO;
import com.smartcity.dto.MonthlyIssueDTO;
import com.smartcity.model.Assignment;
import com.smartcity.model.Department;
import com.smartcity.repository.AssignmentRepository;
import com.smartcity.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of DepartmentService.
 * Handles business logic for managing departments.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @SuppressWarnings("null")
    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public DepartmentStatsDTO getDepartmentStats(Long deptId) {
        long pending = assignmentRepository.countPending(deptId);
        long inProgress = assignmentRepository.countInProgress(deptId);
        long resolved = assignmentRepository.countResolved(deptId);

        return new DepartmentStatsDTO(pending, inProgress, resolved);
    }

    @Override
    public List<MonthlyIssueDTO> getDepartmentMonthlyStats(Long deptId) {
        List<Object[]> rows = assignmentRepository.getMonthlyIssueCount(deptId);
        List<MonthlyIssueDTO> list = new ArrayList<>();

        for (Object[] row : rows) {
            int monthNumber = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();
            String monthName = new DateFormatSymbols().getMonths()[monthNumber - 1];

            list.add(new MonthlyIssueDTO(monthName, count));
        }

        return list;
    }

    @Override
    public Double getAvgResolutionTime(Long deptId) {
        return assignmentRepository.avgResolutionHours(deptId);
    }

    @Override
    public List<Assignment> getAssignments(Long deptId) {
        return assignmentRepository.findByDepartmentId(deptId);
    }
}
