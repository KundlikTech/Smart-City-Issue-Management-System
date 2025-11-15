package com.smartcity.repository;

import com.smartcity.model.WorkerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerLocationRepository extends JpaRepository<WorkerLocation, Long> {

    @Query("""
        SELECT wl FROM WorkerLocation wl
        WHERE wl.worker.id = :workerId
        ORDER BY wl.timestamp DESC
        LIMIT 1
    """)
    WorkerLocation getLatestLocation(Long workerId);

    List<WorkerLocation> findByWorkerIdOrderByTimestampDesc(Long workerId);
}
