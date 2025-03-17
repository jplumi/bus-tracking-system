package com.jplumi.travel_management.service;

import com.jplumi.travel_management.dto.DriverDTO;
import com.jplumi.travel_management.model.Driver;
import com.jplumi.travel_management.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    public List<Driver> getAllDrivers() {
        return repository.findAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Driver getDriverById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Driver createDriver(DriverDTO driverDTO) {
        Driver newDriver = new Driver(null, driverDTO.getName());
        return repository.save(newDriver);
    }

    public void deleteDriver(Long id) {
        repository.deleteById(id);
    }

}
