package com.jplumi.passenger.client;

public interface TripManagementClient {
    boolean validateTripSubscription(Long tripId, Integer stopNumber);
}
