package com.jplumi.passenger.repository;

import com.jplumi.passenger.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
