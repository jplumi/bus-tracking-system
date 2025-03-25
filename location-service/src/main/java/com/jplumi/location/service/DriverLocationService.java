package com.jplumi.location.service;

import com.jplumi.location.dto.UpdateLocationRequest;
import com.jplumi.location.event.DriverLocationEvent;
import com.jplumi.location.model.DriverLocationHistory;
import com.jplumi.location.repository.DriverLocationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverLocationService {

    private final DriverLocationHistoryRepository repository;
    private final KafkaTemplate<String, DriverLocationEvent> kafkaTemplate;

    private static final String LOCATION_KAFKA_TOPIC = "update-driver-location";

    public void updateCurrentLocation(UpdateLocationRequest updateLocationRequest) {
        DriverLocationHistory newLocation = new DriverLocationHistory(
                null,
                updateLocationRequest.getDriverId(),
                updateLocationRequest.getTripId(),
                LocalDateTime.now(),
                updateLocationRequest.getLatitude(),
                updateLocationRequest.getLongitude()
        );

        repository.save(newLocation);

        DriverLocationEvent newEvent = new DriverLocationEvent(
                updateLocationRequest.getDriverId(),
                updateLocationRequest.getTripId(),
                updateLocationRequest.getLatitude(),
                updateLocationRequest.getLongitude()
        );
        kafkaTemplate.send(LOCATION_KAFKA_TOPIC, newEvent);
    }

}
