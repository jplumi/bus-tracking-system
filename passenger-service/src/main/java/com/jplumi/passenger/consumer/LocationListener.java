package com.jplumi.passenger.consumer;

import com.jplumi.passenger.event.DriverLocationEvent;
import com.jplumi.passenger.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationListener {

    private final LocationService locationService;

    private static final String LOCATION_KAFKA_TOPIC = "update-driver-location";

    @KafkaListener(topics = LOCATION_KAFKA_TOPIC)
    public void getMessage(DriverLocationEvent event) {
        locationService.sendLocationToPassengers(event);
    }
}
