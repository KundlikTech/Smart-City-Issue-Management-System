package com.smartcity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private String location;

    private Double latitude;
    private Double longitude;

    private String status = "PENDING";
    private LocalDateTime reportedDate = LocalDateTime.now();
    private LocalDateTime resolvedDate;

    // ✔ DO NOT USE POINT TYPE — use VARCHAR only
    @Column(name = "geo_location")
    private String geoLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
        updateGeoLocationAuto();
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
        updateGeoLocationAuto();
    }

    @PrePersist
    @PreUpdate
    private void updateGeoLocationAuto() {
        if (this.latitude != null && this.longitude != null) {
            this.geoLocation = "POINT(" + this.longitude + " " + this.latitude + ")";
        }
    }
}
