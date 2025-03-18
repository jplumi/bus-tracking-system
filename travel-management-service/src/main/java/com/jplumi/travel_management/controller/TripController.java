package com.jplumi.travel_management.controller;

import com.jplumi.travel_management.dto.CreateTripDTO;
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
        return service.getAllTrips();
    }

    @PostMapping
    public void createTrip(@RequestBody CreateTripDTO trip) {
        service.createTrip(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTripById(@PathVariable Long id) {
        service.deleteTrip(id);
    }

}
