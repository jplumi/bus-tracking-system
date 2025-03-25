package com.jplumi.location.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverLocationEvent {
    private Long driverId;
    private Long tripId;
    private float latitude;
    private float longitude;
}
