package com.jplumi.passenger.controller;

import com.jplumi.passenger.model.Passenger;
import com.jplumi.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @GetMapping
    public List<Passenger> getAllPassengers() {
        return service.getAllPassengers();
    }

    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return service.getPassengerById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        service.deletePassengerById(id);
    }

    @PostMapping
    public Passenger insertPassenger(@RequestBody Passenger passenger) {
        return service.insertPassenger(passenger);
    }
}
