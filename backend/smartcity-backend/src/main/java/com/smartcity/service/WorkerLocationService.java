package com.smartcity.service;

import com.smartcity.model.WorkerLocation;

import java.util.List;

public interface WorkerLocationService {

    WorkerLocation updateLocation(Long workerId, Double lat, Double lng);

    WorkerLocation getLatest(Long workerId);

    List<WorkerLocation> getHistory(Long workerId);
}
