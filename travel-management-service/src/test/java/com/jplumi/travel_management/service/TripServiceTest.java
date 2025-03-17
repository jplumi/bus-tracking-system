package com.jplumi.travel_management.service;

import com.jplumi.travel_management.dto.CreateTripDTO;
import com.jplumi.travel_management.model.Trip;
import com.jplumi.travel_management.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private DriverService driverService;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrip_WhenReceiveTripData_ThenSaveNewTrip() {
        // Arrange
        CreateTripDTO createTripDTO = CreateTripDTO.builder()
                .name("Trip Name")
                .date("2025-04-10")
                .routeId(1L)
                .driverId(1L).build();

        when(driverService.existsById(1L)).thenReturn(true);
        when(routeService.existsById(1L)).thenReturn(true);

        // Act
        tripService.createTrip(createTripDTO);

        // Assert
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void createTrip_WhenRouteNotFound_ThenThrowException() {
        // Arrange
        CreateTripDTO createTripDTO = CreateTripDTO.builder()
                .name("Trip Name")
                .date("2025-04-10")
                .routeId(1L)
                .driverId(1L).build();

        when(driverService.existsById(1L)).thenReturn(true);
        when(routeService.existsById(1L)).thenReturn(false);

        // Act && Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            tripService.createTrip(createTripDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void createTrip_WhenDriverNotFound_ThenThrowException() {
        // Arrange
        CreateTripDTO createTripDTO = CreateTripDTO.builder()
                .name("Trip Name")
                .date("2025-04-10")
                .routeId(1L)
                .driverId(1L).build();

        when(driverService.existsById(1L)).thenReturn(false);
        when(routeService.existsById(1L)).thenReturn(true);

        // Act && Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            tripService.createTrip(createTripDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}