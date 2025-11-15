package com.smartcity.controller;

import com.smartcity.dto.DepartmentStatsDTO;
import com.smartcity.dto.MonthlyIssueDTO;
import com.smartcity.model.Assignment;
import com.smartcity.model.Department;
import com.smartcity.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing city departments (e.g., Roads, Water, Waste, Electricity).
 */
@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend access
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * ✅ Create or update a department
     * Endpoint: POST /api/departments
     */
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Optional<Department> existing = departmentService.findByName(department.getName());
        if (existing.isPresent()) {
            // If exists, update instead of duplicate
            Department existingDept = existing.get();
            existingDept.setContactEmail(department.getContactEmail());
            existingDept.setContactPhone(department.getContactPhone());
            Department updated = departmentService.saveDepartment(existingDept);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }

        Department saved = departmentService.saveDepartment(department);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * ✅ Get all departments
     * Endpoint: GET /api/departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    /**
     * ✅ Get a specific department by ID
     * Endpoint: GET /api/departments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * ✅ Delete a department by ID
     * Endpoint: DELETE /api/departments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/stats")
    public ResponseEntity<DepartmentStatsDTO> getStats(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentStats(id));
    }

    @GetMapping("/{id}/issues")
    public ResponseEntity<List<Assignment>> getIssues(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getAssignments(id));
    }

    @GetMapping("/{id}/monthly")
    public ResponseEntity<List<MonthlyIssueDTO>> getMonthly(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentMonthlyStats(id));
    }

    @GetMapping("/resolve-time/{id}")
    public ResponseEntity<Double> getAvgTime(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getAvgResolutionTime(id));
    }
}
