package com.jplumi.passenger.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationEvent {
    private Long driverId;
    private Long tripId;
    private float latitude;
    private float longitude;
}
