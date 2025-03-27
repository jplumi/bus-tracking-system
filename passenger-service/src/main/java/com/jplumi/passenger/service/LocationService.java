package com.jplumi.passenger.service;

import com.jplumi.passenger.event.DriverLocationEvent;
import com.jplumi.passenger.repository.TripSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LocationService {

    private final ConcurrentHashMap<Long, Set<SseEmitter>> tripEmitters;

    private final TripSubscriptionRepository tripSubscriptionRepository;

    @Autowired
    public LocationService(TripSubscriptionRepository tripSubscriptionRepository) {
        this(tripSubscriptionRepository, new ConcurrentHashMap<>());
    }

    public LocationService(TripSubscriptionRepository tripSubscriptionRepository,
                           ConcurrentHashMap<Long, Set<SseEmitter>> tripEmitters) {
        this.tripSubscriptionRepository = tripSubscriptionRepository;
        this.tripEmitters = tripEmitters;
    }

    public SseEmitter addLocationListener(Long tripId, Long passengerId) {
        if(tripSubscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passenger not subscribed to this trip");
        }

        SseEmitter emitter = new SseEmitter(0L);

        Set<SseEmitter> emitters = tripEmitters.computeIfAbsent(tripId, (key) -> new HashSet<>());
        emitters.add(emitter);

        emitter.onCompletion(() -> removeEmitter(tripId, emitter));
        emitter.onTimeout(() -> removeEmitter(tripId, emitter));
        emitter.onError((error) -> removeEmitter(tripId, emitter));

        return emitter;
    }

    public void sendLocationToPassengers(DriverLocationEvent event) {
        if(tripEmitters.containsKey(event.getTripId())) {
            Set<SseEmitter> emitters = tripEmitters.get(event.getTripId());
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(event);
                } catch (IOException e) {
                    emitter.complete();
                    removeEmitter(event.getTripId(), emitter);
                }
            }
        }
    }

    private void removeEmitter(Long tripId, SseEmitter emitter) {
        tripEmitters.getOrDefault(tripId, Collections.emptySet()).remove(emitter);
        if(tripEmitters.get(tripId).isEmpty()) {
            tripEmitters.remove(tripId);
        }
    }

    // Executed when the application context is closed
    @EventListener(ContextClosedEvent.class)
    public void closeAllConnections() {
        tripEmitters.values().forEach(emittersSet -> {
            for (SseEmitter emitter : emittersSet) {
                emitter.complete();
            }
        });
        tripEmitters.clear();
    }
}
