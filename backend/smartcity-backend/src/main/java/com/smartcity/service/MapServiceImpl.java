package com.smartcity.service;

import com.smartcity.model.Issue;
import com.smartcity.repository.IssueRepository;
import com.smartcity.dto.MapEvent;
import com.smartcity.dto.NearbyIssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Optional<Issue> updateIssueLocation(Long issueId, Double latitude, Double longitude) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        if (issueOpt.isPresent()) {
            Issue issue = issueOpt.get();
            issue.setLatitude(latitude);
            issue.setLongitude(longitude);
            issueRepository.save(issue);

            // Build map event
            MapEvent event = new MapEvent();
            event.setType("LOCATION_UPDATE");
            event.setIssueId(issue.getId());
            event.setIssueTitle(issue.getTitle());
            event.setIssueStatus(issue.getStatus());
            event.setLatitude(latitude);
            event.setLongitude(longitude);
            event.setActor("SYSTEM"); // if authenticated, pass user's email/name
            event.setTimestamp(java.time.LocalDateTime.now());

            // Broadcast to all subscribers
            notificationService.broadcastMapEvent(event);

            // Optionally notify the issue reporter specifically:
            // if (issue.getUser() != null) notificationService.sendMapEventToUser(issue.getUser().getEmail(), event);

            return Optional.of(issue);
        }
        return Optional.empty();
    }

    @Override
    public List<Issue> findNearbyRaw(double latitude, double longitude, double radiusKm) {
        // Fallback simple approach: load all with lat/lng and filter in Java.
        List<Issue> all = issueRepository.findAll();
        return all.stream()
                .filter(i -> i.getLatitude() != null && i.getLongitude() != null)
                .filter(i -> haversineKm(latitude, longitude, i.getLatitude(), i.getLongitude()) <= radiusKm)
                .collect(Collectors.toList());
    }

    private List<NearbyIssueDTO> fallbackNearby(double lat, double lng, double radiusKm) {
        List<Issue> all = issueRepository.findAll();
        List<NearbyIssueDTO> list = new ArrayList<>();
        for (Issue i : all) {
            if (i.getLatitude() == null || i.getLongitude() == null) continue;
            double dist = haversineKm(lat, lng, i.getLatitude(), i.getLongitude());
            if (dist <= radiusKm) {
                list.add(new NearbyIssueDTO(
                        i.getId(),
                        i.getTitle(),
                        i.getDescription(),
                        i.getCategory(),
                        i.getLocation(),
                        i.getLatitude(),
                        i.getLongitude(),
                        round(dist, 3)
                ));
            }
        }
        list.sort(Comparator.comparingDouble(d -> d.getDistanceKm() == null ? Double.MAX_VALUE : d.getDistanceKm()));
        return list;
    }

    @Override
    public List<Issue> findInBoundingBox(double minLat, double maxLat, double minLng, double maxLng) {
        return issueRepository.findByLatitudeBetweenAndLongitudeBetween(minLat, maxLat, minLng, maxLng);
    }

    // Haversine formula: returns distance in kilometers
    private static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        return Math.round(value * factor) / (double) factor;
    }
    @Override
public List<NearbyIssueDTO> getAllIssuesForMap() {
    List<Issue> all = issueRepository.findAll();

    List<NearbyIssueDTO> list = new ArrayList<>();

    for (Issue i : all) {
        if (i.getLatitude() != null && i.getLongitude() != null) {
            list.add(new NearbyIssueDTO(
                    i.getId(),
                    i.getTitle(),
                    i.getDescription(),
                    i.getCategory(),
                    i.getLocation(),
                    i.getLatitude(),
                    i.getLongitude(),
                    0.0
            ));
        }
    }
    return list;
}
@Override
public List<NearbyIssueDTO> findNearby(double lat, double lng, double radiusKm) {

    double radiusMeters = radiusKm * 1000;

    List<Object[]> rows = issueRepository.findNearbyIssues(lat, lng, radiusMeters);

    List<NearbyIssueDTO> list = new ArrayList<>();

    for (Object[] r : rows) {
        list.add(new NearbyIssueDTO(
                ((Number) r[0]).longValue(),
                (String) r[1],
                (String) r[2],
                (String) r[3],
                (String) r[4],
                r[5] != null ? ((Number) r[5]).doubleValue() : null,
                r[6] != null ? ((Number) r[6]).doubleValue() : null,
                round(((Number) r[7]).doubleValue() / 1000, 3)  // convert to km
        ));
    }

    return list;
}

}
