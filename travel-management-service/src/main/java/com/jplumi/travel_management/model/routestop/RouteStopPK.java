package com.jplumi.travel_management.model.routestop;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteStopPK implements Serializable {
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "stop_id")
    private Long stopId;
}
