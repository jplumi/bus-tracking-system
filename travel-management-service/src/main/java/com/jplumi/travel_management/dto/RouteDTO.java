package com.jplumi.travel_management.dto;

import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Stop;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouteDTO {
    private Long routeId;
    private String name;
    private List<RouteStopDTO> stops;

    public static RouteDTO fromRoute(Route route) {
        RouteDTO newDTO = new RouteDTO();
        newDTO.setRouteId(route.getId());
        newDTO.setName(route.getName());

        List<RouteStopDTO> stopsDTO = new ArrayList<>();

        route.getStops().forEach(routeStop -> {
            String expectedTime = routeStop.getExpectedTime().toString();
            Stop stop = routeStop.getStop();
            stopsDTO.add(
                    RouteStopDTO.builder()
                            .stopId(stop.getId())
                            .stopNumber(routeStop.getStopNumber())
                            .expectedTime(expectedTime)
                            .name(stop.getName())
                            .address(stop.getAddress())
                            .latitude(stop.getLatitude())
                            .longitude(stop.getLongitude())
                            .build());
        });
        newDTO.setStops(stopsDTO);

        return newDTO;
    }
}
