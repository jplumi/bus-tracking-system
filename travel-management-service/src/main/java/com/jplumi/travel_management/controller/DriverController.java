package com.jplumi.travel_management.controller;

import com.jplumi.travel_management.dto.DriverDTO;
import com.jplumi.travel_management.model.Driver;
import com.jplumi.travel_management.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;

    @GetMapping
    public List<Driver> getAllDrivers() {
        return service.getAllDrivers();
    }

    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return service.getDriverById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Driver createDriver(@RequestBody DriverDTO driver) {
        return service.createDriver(driver);
    }

    @DeleteMapping("/{id}")
    public void deleteDriverById(@PathVariable Long id) {
        service.deleteDriver(id);
    }

    @GetMapping("/validate-id")
    public boolean validateDriverId(@PathVariable Long driverId) {
        return service.existsById(driverId);
    }

}
