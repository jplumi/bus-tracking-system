package com.jplumi.travel_management.service;

import com.jplumi.travel_management.model.Trip;
import com.jplumi.travel_management.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository repository;

    public List<Trip> getAllTrips() {
        return repository.findAll();
    }

    public Trip createTrip() {
        return null;
    }

}
