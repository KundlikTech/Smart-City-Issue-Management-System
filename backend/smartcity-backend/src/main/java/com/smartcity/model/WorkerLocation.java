package com.smartcity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "worker_locations")
@Data
public class WorkerLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Field worker user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private User worker;

    private Double latitude;
    private Double longitude;

    private LocalDateTime timestamp = LocalDateTime.now();
}
