package com.jplumi.passenger.dto;

import lombok.Data;

@Data
public class SubscribeRequestDTO {
    private Long tripId;
    private Integer stopNumber;
}
