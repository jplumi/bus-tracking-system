package com.jplumi.travel_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteStopDTO {
    private Long stopId;
    private int stopNumber;
    private String expectedTime;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
