package com.jplumi.passenger.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TripManagementClientImpl implements TripManagementClient {

    WebClient client = WebClient.create("http://localhost:8082");

    @Override
    public boolean validateTripSubscription(Long tripId, Integer stopNumber) {
        Boolean result = client.get().uri(uriBuilder -> uriBuilder
                    .path("/trips/validate-subscription")
                    .queryParam("tripId", tripId)
                    .queryParam("stopNumber", stopNumber)
                    .build())
                .retrieve()
                .bodyToMono(Boolean.class).block();

        return Boolean.TRUE.equals(result);
    }

}
