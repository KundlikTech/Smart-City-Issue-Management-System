package com.smartcity.repository;

import com.smartcity.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.department.id = :deptId AND a.issue.status = 'PENDING'")
    long countPending(@Param("deptId") Long deptId);

    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.department.id = :deptId AND a.issue.status = 'IN_PROGRESS'")
    long countInProgress(@Param("deptId") Long deptId);

    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.department.id = :deptId AND a.issue.status = 'RESOLVED'")
    long countResolved(@Param("deptId") Long deptId);

    @Query("""
                SELECT MONTH(a.issue.reportedDate) as month, COUNT(a)
                FROM Assignment a
                WHERE a.department.id = :deptId
                GROUP BY MONTH(a.issue.reportedDate)
                ORDER BY month
            """)
    List<Object[]> getMonthlyIssueCount(@Param("deptId") Long deptId);

    @Query("""
                SELECT AVG(TIMESTAMPDIFF(HOUR, a.issue.reportedDate, a.issue.resolvedDate))
                FROM Assignment a
                WHERE a.department.id = :deptId AND a.issue.resolvedDate IS NOT NULL
            """)
    Double avgResolutionHours(@Param("deptId") Long deptId);

    @Query("""
  SELECT a.department.id, a.department.name, COUNT(a)
  FROM Assignment a
  WHERE a.issue.reportedDate >= :from
  GROUP BY a.department.id, a.department.name
  ORDER BY COUNT(a) DESC
""")
List<Object[]> topDepartmentsSince(@Param("from") LocalDateTime from);
@Query(value = """
    SELECT 
        a.issue.id,
        a.issue.title,
        a.issue.status,
        a.issue.reported_date,
        d.name
    FROM assignments a
    JOIN issues i ON i.id = a.issue_id
    JOIN departments d ON d.id = a.department_id
    WHERE i.resolved_date IS NULL
    AND TIMESTAMPDIFF(HOUR, i.reported_date, NOW()) > :slaHours
""", nativeQuery = true)
List<Object[]> findSlaBreaches(@Param("slaHours") long slaHours);
@Query(value = """
    SELECT AVG(TIMESTAMPDIFF(HOUR, i.reported_date, a.assigned_date))
    FROM assignments a
    JOIN issues i ON i.id = a.issue_id
""", nativeQuery = true)
Double avgResponseHours();
List<Assignment> findByStatus(String status);



    List<Assignment> findByDepartmentId(Long deptId);

}
