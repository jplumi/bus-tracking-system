package com.jplumi.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
public class DriverLocationHistory {
    @Id
    private String id;
    private Long driverId;
    private Long tripId;
    private LocalDateTime timestamp;
    private float latitude;
    private float longitude;
}
