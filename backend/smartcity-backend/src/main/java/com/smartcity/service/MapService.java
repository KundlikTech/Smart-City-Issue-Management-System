package com.smartcity.service;

import com.smartcity.model.Issue;
import com.smartcity.dto.NearbyIssueDTO;

import java.util.List;
import java.util.Optional;

public interface MapService {
    Optional<Issue> updateIssueLocation(Long issueId, Double latitude, Double longitude);
    List<Issue> findNearbyRaw(double latitude, double longitude, double radiusKm); // existing raw entities
    List<NearbyIssueDTO> findNearby(double latitude, double longitude, double radiusKm); // new DTO list
    List<Issue> findInBoundingBox(double minLat, double maxLat, double minLng, double maxLng);
    List<NearbyIssueDTO> getAllIssuesForMap();

}
