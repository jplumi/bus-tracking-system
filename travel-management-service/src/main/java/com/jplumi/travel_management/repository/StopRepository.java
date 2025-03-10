package com.jplumi.travel_management.repository;

import com.jplumi.travel_management.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Optional<Stop> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
