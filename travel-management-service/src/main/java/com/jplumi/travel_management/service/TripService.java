package com.jplumi.travel_management.service;

import com.jplumi.travel_management.dto.CreateTripDTO;
import com.jplumi.travel_management.model.Driver;
import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Trip;
import com.jplumi.travel_management.model.routestop.RouteStop;
import com.jplumi.travel_management.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository repository;

    private final DriverService driverService;
    private final RouteService routeService;

    public List<Trip> getAllTrips() {
        return repository.findAll();
    }

    @Transactional
    public void createTrip(CreateTripDTO dto) {
        if(!driverService.existsById(dto.getDriverId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Driver with id %s not found", dto.getDriverId()));
        }

        if(!routeService.existsById(dto.getRouteId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Route with id %s not found", dto.getRouteId()));
        }

        Driver driver = new Driver();
        driver.setId(dto.getDriverId());

        Route route = new Route();
        route.setId(dto.getRouteId());

        LocalDate tripDate = LocalDate.parse(dto.getDate(), DateTimeFormatter.ISO_DATE);

        Trip newTrip = Trip.builder()
                .name(dto.getName())
                .date(tripDate)
                .driver(driver)
                .route(route).build();

        repository.save(newTrip);
    }

    public void deleteTrip(Long id) {
        repository.deleteById(id);
    }

    public boolean validateSubscription(Long tripId, Integer stopNumber) {
        Optional<Trip> optionalTrip = repository.findById(tripId);
        if(optionalTrip.isEmpty()) {
            return false;
        }
        return optionalTrip.get().getRoute().getStops().size() <= stopNumber;
    }
}
