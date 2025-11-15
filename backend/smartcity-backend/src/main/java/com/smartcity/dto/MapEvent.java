package com.smartcity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Payload sent for live map updates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapEvent {
    // Event type: LOCATION_UPDATE, OFFICER_LOCATION, ISSUE_ASSIGNED, ISSUE_RESOLVED, MARKER_CLICK etc.
    private String type;

    // Issue-specific
    private Long issueId;
    private String issueTitle;
    private String issueStatus;

    // Lat/lng for marker or officer
    private Double latitude;
    private Double longitude;

    // Optional extra info
    private String extra;        // free text (e.g. "Assigned to Road Dept")
    private String actor;        // who triggered the event (email/name)

    private LocalDateTime timestamp;
}
