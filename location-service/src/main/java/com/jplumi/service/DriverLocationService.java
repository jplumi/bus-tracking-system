package com.jplumi.service;

import com.jplumi.dto.UpdateLocationRequest;
import com.jplumi.model.DriverLocationHistory;
import com.jplumi.repository.DriverLocationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverLocationService {

    private final DriverLocationHistoryRepository repository;

    public void updateCurrentLocation(UpdateLocationRequest updateLocationRequest) {
        DriverLocationHistory newLocation = new DriverLocationHistory(
                null,
                updateLocationRequest.getDriverId(),
                LocalDateTime.now(),
                updateLocationRequest.getLatitude(),
                updateLocationRequest.getLongitude()
        );

        repository.save(newLocation);
    }

}
