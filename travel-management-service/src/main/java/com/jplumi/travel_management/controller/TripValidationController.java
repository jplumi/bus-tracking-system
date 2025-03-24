package com.jplumi.travel_management.controller;

import com.jplumi.travel_management.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripValidationController {

    private final TripService tripService;

    @GetMapping("/validate-subscription")
    public boolean validateTripSubscription(@RequestParam Long tripId, @RequestParam Integer stopNumber) {
        return tripService.validateSubscription(tripId, stopNumber);
    }

}
