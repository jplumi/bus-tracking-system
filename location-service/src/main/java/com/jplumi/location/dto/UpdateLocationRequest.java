package com.jplumi.location.dto;

import lombok.Data;

@Data
public class UpdateLocationRequest {
    private Long driverId;
    private float latitude;
    private float longitude;
}
