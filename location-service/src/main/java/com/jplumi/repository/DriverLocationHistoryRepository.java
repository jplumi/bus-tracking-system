package com.jplumi.repository;

import com.jplumi.model.DriverLocationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverLocationHistoryRepository extends MongoRepository<DriverLocationHistory, Long> {
}
