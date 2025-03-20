package com.jplumi.passenger.service;

import com.jplumi.passenger.client.TripManagementClient;
import com.jplumi.passenger.model.Passenger;
import com.jplumi.passenger.model.TripSubscription;
import com.jplumi.passenger.repository.TripSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TripSubscriptionServiceTest {

    @Mock
    private TripSubscriptionRepository subscriptionRepository;

    @Mock
    private TripManagementClient tripManagementClient;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private TripSubscriptionService subscriptionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void subscribePassenger_WhenTripAndStopAreValid_ThenSaveTripSubscription() {
        // Given
        Long passengerId = 1L;
        Long tripId = 1L;
        Integer stopNumber = 2;

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        TripSubscription savedSubscription = new TripSubscription();
        savedSubscription.setPassenger(passenger);
        savedSubscription.setTripId(tripId);
        savedSubscription.setStopNumber(stopNumber);

        when(passengerService.existsById(passengerId)).thenReturn(true);
        when(subscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId))
                .thenReturn(Optional.empty());
        when(tripManagementClient.validateTripSubscription(tripId, stopNumber)).thenReturn(true);
        when(subscriptionRepository.save(any(TripSubscription.class))).thenReturn(savedSubscription);

        // When
        TripSubscription subscription = subscriptionService.subscribePassenger(passengerId, tripId, stopNumber);

        // Then
        assertThat(subscription).isNotNull();
        verify(passengerService, times(1)).existsById(passengerId);
        verify(tripManagementClient, times(1)).validateTripSubscription(tripId, stopNumber);
        verify(subscriptionRepository, times(1)).save(savedSubscription);
    }

    @Test
    void subscribePassenger_WhenPassengerNotExists_ThenThrowException() {
        // Given
        Long passengerId = 1L;
        when(passengerService.existsById(passengerId)).thenReturn(false);

        // When && Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.subscribePassenger(passengerId, 1L, 2);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(passengerService, times(1)).existsById(passengerId);
    }

    @Test
    void subscribePassenger_WhenAlreadySubscribed_ThenThrowsException() {
        // Given
        Long passengerId = 1L;
        Long tripId = 1L;

        when(passengerService.existsById(passengerId)).thenReturn(true);
        when(subscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId))
                .thenReturn(Optional.of(new TripSubscription())); // already subscribed

        // When && Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.subscribePassenger(passengerId, tripId, 2);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(subscriptionRepository, times(1)).findByPassengerIdAndTripId(passengerId, tripId);
    }

    @Test
    void subscribePassenger_WhenTripAndStopNotValid_ThenThrowException() {
        // Given
        Long passengerId = 1L;
        Long tripId = 1L;
        Integer stopNumber = 2;

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        when(passengerService.existsById(passengerId)).thenReturn(true);
        when(subscriptionRepository.findByPassengerIdAndTripId(passengerId, tripId))
                .thenReturn(Optional.empty());

        // trip NOT valid
        when(tripManagementClient.validateTripSubscription(tripId, stopNumber)).thenReturn(false);

        // When && Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.subscribePassenger(passengerId, tripId, stopNumber);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(passengerService, times(1)).existsById(passengerId);
        verify(tripManagementClient, times(1)).validateTripSubscription(tripId, stopNumber);
        verify(subscriptionRepository, times(0)).save(any());
    }
}