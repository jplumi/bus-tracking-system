package com.jplumi.dto;

import lombok.Data;

@Data
public class UpdateLocationRequest {
    private Long driverId;
    private float latitude;
    private float longitude;
}
