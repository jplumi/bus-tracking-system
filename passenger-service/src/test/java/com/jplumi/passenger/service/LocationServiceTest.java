package com.jplumi.passenger.service;

import com.jplumi.passenger.event.DriverLocationEvent;
import com.jplumi.passenger.model.TripSubscription;
import com.jplumi.passenger.repository.TripSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LocationServiceTest {

    @Mock
    private TripSubscriptionRepository tripSubscriptionRepository;

    private ConcurrentHashMap<Long, Set<SseEmitter>> tripEmitters;

    @InjectMocks
    private LocationService locationService;

    private final Long passengerId = 1L;
    private final Long tripId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        tripEmitters = new ConcurrentHashMap<>();
        locationService = new LocationService(tripSubscriptionRepository, tripEmitters);
    }

    @Test
    void addLocationListener_WhenPassengerIsSubscribed_ThenAddEmitter() {
        // Given
        when(tripSubscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId))
                .thenReturn(Optional.of(new TripSubscription()));

        // When
        SseEmitter emitter = locationService.addLocationListener(tripId, passengerId);

        // Then
        assertThat(emitter).isNotNull();
    }

    @Test
    void addLocationListener_WhenPassengerIsNotSubscribed_ThenThrowException() {
        // Given
        when(tripSubscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId))
                .thenReturn(Optional.empty());

        // When && Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> locationService.addLocationListener(tripId, passengerId));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void sendLocationToPassengers_ShouldSendEventToEmitters() throws IOException {
        // Given
        DriverLocationEvent event = new DriverLocationEvent();
        event.setTripId(tripId);

        SseEmitter emitter = mock(SseEmitter.class);
        tripEmitters.put(tripId, Set.of(emitter));

        // When
        locationService.sendLocationToPassengers(event);

        // Then
        verify(emitter, times(1)).send(event);
    }
}