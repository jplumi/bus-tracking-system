package com.jplumi.travel_management.repository;

import com.jplumi.travel_management.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
