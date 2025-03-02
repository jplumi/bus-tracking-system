package com.jplumi.location.repository;

import com.jplumi.location.model.DriverLocationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverLocationHistoryRepository extends MongoRepository<DriverLocationHistory, Long> {
}
