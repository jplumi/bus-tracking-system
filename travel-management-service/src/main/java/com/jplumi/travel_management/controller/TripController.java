package com.jplumi.travel_management.controller;

import com.jplumi.travel_management.model.Trip;
import com.jplumi.travel_management.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService service;

    @GetMapping
    public List<Trip> getAllTrips() {
        return null;
    }

    @PostMapping
    public Trip insertTrip(@RequestBody Trip trip) {
        return null;
    }

}
