package com.smartcity.controller;

import com.smartcity.model.WorkerLocation;
import com.smartcity.service.WorkerLocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkerTrackingController {

    @Autowired
    private WorkerLocationService workerLocationService;

    // 📍 Update GPS Location (called every 5-10 seconds from mobile app)
    @PostMapping("/update-location")
    public ResponseEntity<?> updateLocation(@RequestParam Long workerId,
                                            @RequestBody Map<String, Double> body) {

        Double lat = body.get("latitude");
        Double lng = body.get("longitude");

        if (lat == null || lng == null) {
            return ResponseEntity.badRequest().body("latitude & longitude required");
        }

        return ResponseEntity.ok(workerLocationService.updateLocation(workerId, lat, lng));
    }

    // 📌 Get latest location of any worker
    @GetMapping("/{workerId}/latest")
    public ResponseEntity<WorkerLocation> getLatest(@PathVariable Long workerId) {
        return ResponseEntity.ok(workerLocationService.getLatest(workerId));
    }

    // 📜 Movement history
    @GetMapping("/{workerId}/history")
    public ResponseEntity<List<WorkerLocation>> getHistory(@PathVariable Long workerId) {
        return ResponseEntity.ok(workerLocationService.getHistory(workerId));
    }
}
