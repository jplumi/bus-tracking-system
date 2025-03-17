package com.jplumi.travel_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTripDTO {
    private String name;
    private String date;
    private Long driverId;
    private Long routeId;
}
