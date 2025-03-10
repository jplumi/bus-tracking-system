package com.jplumi.travel_management.service;

import com.jplumi.travel_management.dto.RouteDTO;
import com.jplumi.travel_management.dto.RouteStopDTO;
import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Stop;
import com.jplumi.travel_management.model.routestop.RouteStop;
import com.jplumi.travel_management.repository.RouteRepository;
import com.jplumi.travel_management.repository.RouteStopRepository;
import com.jplumi.travel_management.repository.StopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;
    @Mock
    private StopRepository stopRepository;
    @Mock
    private RouteStopRepository routeStopRepository;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoutes_ShouldReturnRouteDTOList() {
        // Arrange
        Route route1 = createRoute();
        route1.setId(1L);
        Route route2 = createRoute();
        route2.setId(2L);

        when(routeRepository.findAll()).thenReturn(List.of(route1, route2));

        // Act
        List<RouteDTO> result = routeService.getAllRoutes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(route1.getId(), result.get(0).getRouteId());
        assertEquals(route2.getId(), result.get(1).getRouteId());
        assertEquals("2025-03-10T12:00", result.get(0).getStops().get(0).getExpectedTime());
        assertEquals("2025-03-10T13:30", result.get(0).getStops().get(1).getExpectedTime());
    }

    @Test
    void createRoute_WhenStopsExist_ThenSaveOnlyRoute() {
        // Arrange
        RouteDTO inputRouteDTO = createExampleRouteDTO();

        RouteStopDTO stop1 = inputRouteDTO.getStops().get(0);
        RouteStopDTO stop2 = inputRouteDTO.getStops().get(1);

        Stop returnedStop1 = Stop.builder().id(1L).build();
        Stop returnedStop2 = Stop.builder().id(2L).build();

        when(stopRepository.findByLatitudeAndLongitude(stop1.getLatitude(), stop1.getLongitude()))
                .thenReturn(Optional.of(returnedStop1));
        when(stopRepository.findByLatitudeAndLongitude(stop2.getLatitude(), stop2.getLongitude()))
                .thenReturn(Optional.of(returnedStop2));

        Route returnedRoute = new Route();
        returnedRoute.setId(1L);
        when(routeRepository.save(any())).thenReturn(returnedRoute);

        // Act
        RouteDTO result = routeService.createRoute(inputRouteDTO);

        // Assert
        verify(routeRepository, times(1)).save(any(Route.class));

        assertNotNull(result);
        assertNotNull(result.getRouteId());

        List<RouteStopDTO> resultStops = result.getStops().stream().toList();
        assertNotNull(resultStops.get(0).getStopId());
        assertNotNull(resultStops.get(1).getStopId());
    }

    @Test
    void createRoute_WhenStopsDoNotExist_ThenSaveStopsAndRoute() {
        // Arrange
        RouteDTO inputRouteDTO = createExampleRouteDTO();

        RouteStopDTO stop1 = inputRouteDTO.getStops().get(0);
        RouteStopDTO stop2 = inputRouteDTO.getStops().get(1);

        when(stopRepository.findByLatitudeAndLongitude(stop1.getLatitude(), stop1.getLongitude()))
                .thenReturn(Optional.empty());
        when(stopRepository.findByLatitudeAndLongitude(stop2.getLatitude(), stop2.getLongitude()))
                .thenReturn(Optional.empty());

        Stop returnedStop = Stop.builder().id(1L).build();
        when(stopRepository.save(any(Stop.class))).thenReturn(returnedStop);

        Route returnedRoute = new Route();
        returnedRoute.setId(1L);
        when(routeRepository.save(any())).thenReturn(returnedRoute);

        // Act
        RouteDTO result = routeService.createRoute(inputRouteDTO);

        // Assert
        verify(routeRepository, times(1)).save(any(Route.class));
        verify(stopRepository, times(2)).save(any(Stop.class));

        assertNotNull(result);
        assertNotNull(result.getRouteId());

        List<RouteStopDTO> resultStops = result.getStops().stream().toList();
        assertNotNull(resultStops.get(0).getStopId());
        assertNotNull(resultStops.get(1).getStopId());
    }

    private Route createRoute() {
        // create route
        Route route = new Route();
        route.setId(1L);
        route.setName("RouteName");

        // create RouteStop 1
        Stop stop1 = Stop.builder()
                .id(1L)
                .name("Gas Station")
                .address("Something Street, 123")
                .latitude(12.0)
                .longitude(53.0).build();

        RouteStop routeStop1 = RouteStop.builder()
                .route(route)
                .stop(stop1)
                .stopNumber(0)
                .expectedTime(LocalDateTime.of(2025, 3, 10, 12, 0))
                .build();

        // create RouteStop 2
        Stop stop2 = Stop.builder()
                .id(2L)
                .name("Supermarket")
                .address("Somewhere Street, 321")
                .latitude(13.1)
                .longitude(59.5).build();

        RouteStop routeStop2 = RouteStop.builder()
                .route(route)
                .stop(stop2)
                .stopNumber(1)
                .expectedTime(LocalDateTime.of(2025, 3, 10, 13, 30))
                .build();

        route.setStops(Set.of(routeStop1, routeStop2));
        return route;
    }

    private RouteDTO createExampleRouteDTO() {
        RouteStopDTO routeStop1 = RouteStopDTO.builder()
                .stopNumber(0)
                .name("Gas Station")
                .address("Something Street, 123")
                .expectedTime("2025-03-10T12:00")
                .latitude(12.0)
                .longitude(53.0).build();
        
        RouteStopDTO routeStop2 = RouteStopDTO.builder()
                .stopNumber(1)
                .name("Supermarket")
                .address("Somewhere Street, 321")
                .expectedTime("2025-03-10T13:30")
                .latitude(13.1)
                .longitude(59.5).build();

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setName("Route Name");
        routeDTO.setStops(Arrays.asList(routeStop1, routeStop2));
        
        return routeDTO;
    }
}