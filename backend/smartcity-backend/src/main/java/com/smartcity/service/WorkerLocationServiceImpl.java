package com.smartcity.service;

import com.smartcity.model.User;
import com.smartcity.model.WorkerLocation;
import com.smartcity.repository.UserRepository;
import com.smartcity.repository.WorkerLocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerLocationServiceImpl implements WorkerLocationService {

    @Autowired
    private WorkerLocationRepository workerLocationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WorkerLocation updateLocation(Long workerId, Double lat, Double lng) {

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        WorkerLocation wl = new WorkerLocation();
        wl.setWorker(worker);
        wl.setLatitude(lat);
        wl.setLongitude(lng);

        return workerLocationRepository.save(wl);
    }

    @Override
    public WorkerLocation getLatest(Long workerId) {
        return workerLocationRepository.getLatestLocation(workerId);
    }

    @Override
    public List<WorkerLocation> getHistory(Long workerId) {
        return workerLocationRepository.findByWorkerIdOrderByTimestampDesc(workerId);
    }
}
