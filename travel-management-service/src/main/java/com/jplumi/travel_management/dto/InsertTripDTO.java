package com.jplumi.travel_management.dto;

import lombok.Data;

@Data
public class InsertTripDTO {
    private String name;
    private Long driverId;
    private Long routeId;
}
