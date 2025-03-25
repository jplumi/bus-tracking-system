package com.jplumi.passenger.service;

import com.jplumi.passenger.client.TravelManagementClient;
import com.jplumi.passenger.model.Passenger;
import com.jplumi.passenger.model.TripSubscription;
import com.jplumi.passenger.repository.TripSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TripSubscriptionService {

    private final TripSubscriptionRepository repository;
    private final TravelManagementClient tripManagementClient;
    private final PassengerService passengerService;

    @Transactional
    public TripSubscription subscribePassenger(Long passengerId, Long tripId, Integer stopNumber) {
        if(!passengerService.existsById(passengerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger does not exists.");
        }

        if(repository.findByPassengerIdAndTripId(passengerId, tripId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already subscribed to trip.");
        }

        boolean isValid = tripManagementClient.validateTripSubscription(tripId, stopNumber);
        if(!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip or stop is not valid");
        }

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        TripSubscription subscription = TripSubscription.builder()
                .passenger(passenger)
                .tripId(tripId)
                .stopNumber(stopNumber).build();

        return repository.save(subscription);
    }

    public void deleteSubscription(Long subscriptionId) {
        repository.deleteById(subscriptionId);
    }
}
