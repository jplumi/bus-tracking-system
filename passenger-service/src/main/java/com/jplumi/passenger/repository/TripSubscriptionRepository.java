package com.jplumi.passenger.repository;

import com.jplumi.passenger.model.TripSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripSubscriptionRepository extends JpaRepository<TripSubscription, Long> {
    Optional<TripSubscription> findByPassengerIdAndTripId(Long passengerId, Long tripId);
}
