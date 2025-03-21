package com.jplumi.passenger.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("travel-management-service")
public interface TripManagementClient {
    @GetMapping("/trips/validate-subscription")
    boolean validateTripSubscription(@RequestParam Long tripId, @RequestParam Integer stopNumber);
}
