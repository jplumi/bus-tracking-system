package com.jplumi.travel_management.repository;

import com.jplumi.travel_management.model.routestop.RouteStop;
import com.jplumi.travel_management.model.routestop.RouteStopPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStopRepository extends JpaRepository<RouteStop, RouteStopPK> {
}
