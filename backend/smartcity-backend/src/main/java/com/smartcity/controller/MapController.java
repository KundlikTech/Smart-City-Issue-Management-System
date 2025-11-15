package com.smartcity.controller;

import com.smartcity.dto.NearbyIssueDTO;
import com.smartcity.model.Issue;
import com.smartcity.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "http://localhost:3000")
public class MapController {

    @Autowired
    private MapService mapService;

    /**
     * Update an issue location (latitude/longitude).
     * POST /api/map/update-location?issueId=1
     * Body JSON: { "latitude": 18.5204, "longitude": 73.8567 }
     */
    @PostMapping("/update-location")
public ResponseEntity<?> updateLocation(@RequestParam Long issueId, @RequestBody Map<String, Double> body) {
    Double lat = body.get("latitude");
    Double lng = body.get("longitude");
    if (lat == null || lng == null) {
        return ResponseEntity.badRequest().body("latitude and longitude are required");
    }
    Optional<Issue> updated = mapService.updateIssueLocation(issueId, lat, lng);
    return updated
            .<ResponseEntity<?>>map(issue -> ResponseEntity.ok(issue))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found"));
}


    /**
     * Nearby search (returns DTO with distanceKm).
     * GET /api/map/nearby?lat=18.52&lng=73.85&radiusKm=5
     */
    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyIssueDTO>> nearby(@RequestParam double lat,
                                                       @RequestParam double lng,
                                                       @RequestParam(defaultValue = "5") double radiusKm) {
        List<NearbyIssueDTO> list = mapService.findNearby(lat, lng, radiusKm);
        return ResponseEntity.ok(list);
    }

    /**
     * Bounding box search
     * GET /api/map/bbox?minLat=18.5&maxLat=18.6&minLng=73.8&maxLng=73.9
     */
    @GetMapping("/bbox")
    public ResponseEntity<List<Issue>> bbox(@RequestParam double minLat,
                                            @RequestParam double maxLat,
                                            @RequestParam double minLng,
                                            @RequestParam double maxLng) {
        List<Issue> list = mapService.findInBoundingBox(minLat, maxLat, minLng, maxLng);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/all")
    public ResponseEntity<List<NearbyIssueDTO>> getAllIssuesForMap() {
        List<NearbyIssueDTO> list = mapService.getAllIssuesForMap();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/radius")
public ResponseEntity<List<NearbyIssueDTO>> getIssuesInRadius(
        @RequestParam double lat,
        @RequestParam double lng,
        @RequestParam double radiusKm) {

    List<NearbyIssueDTO> list = mapService.findNearby(lat, lng, radiusKm);
    return ResponseEntity.ok(list);
}


}
