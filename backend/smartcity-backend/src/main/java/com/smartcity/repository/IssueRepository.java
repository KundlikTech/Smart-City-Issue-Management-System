package com.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcity.model.Issue;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    // Get all issues by user ID
    List<Issue> findByUserId(Long userId);

    // Get all issues by status (PENDING, IN_PROGRESS, RESOLVED)
    List<Issue> findByStatus(String status);

    long count();
    long countByStatus(String status);

    Page<Issue> findAllByStatus(String status, Pageable pageable);

    List<Issue> findByReportedDateAfter(LocalDateTime date);

    // Find issues inside a bounding box (fast)
    List<Issue> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLng, Double maxLng);

    /**
     * Native spatial query — returns rows:
     * id, title, description, category, location, latitude, longitude, distance(meters)
     *
     * NOTE: radius parameter is in meters.
     *
     * Make sure the table column name is `geo_point` (MySQL POINT). Adjust if your column name differs.
     */
    @Query(value = """
SELECT 
    id, title, description, category, location, latitude, longitude,
    ST_Distance_Sphere(geo_point, POINT(:lng, :lat)) AS distance
FROM issues
WHERE geo_point IS NOT NULL
AND ST_Distance_Sphere(geo_point, POINT(:lng, :lat)) <= :radius
ORDER BY distance ASC
""", nativeQuery = true)
    List<Object[]> findNearbyIssues(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radiusMeters
    );

    @Query(value = """
    SELECT DATE(reported_date) AS day, COUNT(*) AS count
    FROM issues
    WHERE reported_date >= DATE_SUB(CURDATE(), INTERVAL 6 DAY)
    GROUP BY day
    ORDER BY day;
""", nativeQuery = true)
    List<Object[]> countLast7Days();

    @Query(value = """
    SELECT DATE_FORMAT(reported_date, '%Y-%m') AS month, COUNT(*) AS count
    FROM issues
    WHERE reported_date >= DATE_SUB(CURDATE(), INTERVAL 5 MONTH)
    GROUP BY month
    ORDER BY month;
""", nativeQuery = true)
    List<Object[]> countLast6Months();

    // Example JPA query if you need one (optional)
    @Query("""
    SELECT MONTH(i.reportedDate) AS month, COUNT(i) 
    FROM Issue i 
    WHERE i.reportedDate >= :from 
    GROUP BY MONTH(i.reportedDate) 
    ORDER BY month
    """)
    List<Object[]> countIssuesSince(@Param("from") LocalDateTime from);
}
