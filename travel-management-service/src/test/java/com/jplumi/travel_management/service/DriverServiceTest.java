package com.jplumi.travel_management.service;

import com.jplumi.travel_management.model.Driver;
import com.jplumi.travel_management.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDriverById_WhenIdFound_ThenReturnDriver() {
        // Arrange
        Long id = 1L;
        Driver newDriver = new Driver();
        newDriver.setId(id);
        when(driverRepository.findById(id)).thenReturn(Optional.of(newDriver));

        // Act
        Driver result = driverService.getDriverById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getDriverById_WhenIdNotFound_ThenThrowException() {
        // Arrange
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        // Act && Assert
        ResponseStatusException resultException = assertThrows(ResponseStatusException.class, () -> {
            driverService.getDriverById(id);
        });
        assertEquals(HttpStatus.NOT_FOUND, resultException.getStatusCode());
    }
}