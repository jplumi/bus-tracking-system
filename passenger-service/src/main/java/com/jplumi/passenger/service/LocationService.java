package com.jplumi.passenger.service;

import com.jplumi.passenger.event.DriverLocationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendLocationUpdate(DriverLocationEvent locationEvent) {
        messagingTemplate.convertAndSend("/topic/trip", locationEvent);
    }

}
