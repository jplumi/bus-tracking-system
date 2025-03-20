package com.jplumi.passenger.client;

import org.springframework.stereotype.Service;

@Service
public class TripManagementClientImpl implements TripManagementClient {

    @Override
    public boolean validateTripSubscription(Long tripId, Integer stopNumber) {
        return true;
    }

}
