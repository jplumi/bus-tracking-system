package com.jplumi.passenger.service;

import com.jplumi.passenger.model.Passenger;
import com.jplumi.passenger.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public List<Passenger> getAllPassengers() {
        return repository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Passenger insertPassenger(Passenger passenger) {
        return repository.save(passenger);
    }

    public void deletePassengerById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
