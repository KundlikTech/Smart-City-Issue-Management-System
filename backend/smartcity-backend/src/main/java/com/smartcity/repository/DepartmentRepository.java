package com.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcity.model.Department;
import java.util.Optional;

/**
 * Repository interface for managing Department entity.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Find department by name
    Optional<Department> findByName(String name);
}
