package com.jplumi.passenger.controller;

import com.jplumi.passenger.dto.SubscribeRequestDTO;
import com.jplumi.passenger.model.TripSubscription;
import com.jplumi.passenger.service.TripSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class TripSubscriptionController {

    private final TripSubscriptionService service;

    @PostMapping("/{passengerId}/subscribe")
    public TripSubscription subscribePassengerToTrip(
            @PathVariable Long passengerId,
            @RequestBody SubscribeRequestDTO requestBody) {

        return service.subscribePassenger(passengerId, requestBody.getTripId(), requestBody.getStopNumber());
    }

    @DeleteMapping("/subscription/{subscriptionId}")
    public void deleteSubscription(@PathVariable Long subscriptionId) {
        service.deleteSubscription(subscriptionId);
    }
}
