package com.jplumi.travel_management.service;

import com.jplumi.travel_management.dto.RouteDTO;
import com.jplumi.travel_management.dto.RouteStopDTO;
import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Stop;
import com.jplumi.travel_management.model.routestop.RouteStop;
import com.jplumi.travel_management.model.routestop.RouteStopPK;
import com.jplumi.travel_management.repository.RouteRepository;
import com.jplumi.travel_management.repository.RouteStopRepository;
import com.jplumi.travel_management.repository.StopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository repository;
    private final RouteStopRepository routeStopRepository;
    private final StopRepository stopRepository;

    public List<RouteDTO> getAllRoutes() {
        List<Route> routes = repository.findAll();
        return routes.stream().map(RouteDTO::fromRoute).toList();
    }

    @Transactional
    public RouteDTO createRoute(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.getName());
        route = repository.save(route);

        Set<RouteStop> routeStops = new HashSet<>();
        for(var routeStopDTO : routeDTO.getStops()) {
            RouteStop routeStop = dtoToRouteStop(routeStopDTO);

            Stop existingStop = stopRepository
                    .findByLatitudeAndLongitude(routeStopDTO.getLatitude(), routeStopDTO.getLongitude())
                    .orElseGet(() -> stopRepository.save(routeStop.getStop()));

            routeStop.setStop(existingStop);
            routeStop.setRoute(route);
            routeStop.setId(new RouteStopPK(route.getId(), existingStop.getId()));

            routeStops.add(routeStop);
        }

        routeStopRepository.saveAll(routeStops);
        route.setStops(routeStops);
        return RouteDTO.fromRoute(route);
    }

    public void deleteRouteById(Long id) {
       repository.deleteById(id);
    }

    private Route dtoToRoute(RouteDTO dto) {
        Route route = new Route();
        route.setName(dto.getName());

        Set<RouteStop> routeStops = dto.getStops().stream()
                .map(this::dtoToRouteStop)
                .collect(Collectors.toSet());
        route.setStops(routeStops);

        return route;
    }

    private RouteStop dtoToRouteStop(RouteStopDTO dto) {
        Stop stop = Stop.builder()
                .id(dto.getStopId())
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude()).build();

        LocalDateTime timestamp = LocalDateTime.parse(dto.getExpectedTime(), DateTimeFormatter.ISO_DATE_TIME);

        return RouteStop.builder()
                .stop(stop)
                .stopNumber(dto.getStopNumber())
                .expectedTime(timestamp)
                .build();
    }

    private List<Stop> extractStopsFromRouteDTO(RouteDTO routeDTO) {
        List<Stop> stops = new ArrayList<>();
        for (RouteStopDTO dto : routeDTO.getStops()) {
            stops.add(Stop.builder()
                    .id(dto.getStopId())
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude()).build()
            );
        }
        return stops;
    }

}
