package com.jplumi.service;

import com.jplumi.dto.UpdateLocationRequest;
import com.jplumi.event.DriverLocationEvent;
import com.jplumi.model.DriverLocationHistory;
import com.jplumi.repository.DriverLocationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverLocationServiceTest {

    @Mock
    private DriverLocationHistoryRepository repository;

    @Mock
    private KafkaTemplate<String, DriverLocationEvent> kafkaTemplate;

    @InjectMocks
    private DriverLocationService driverLocationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenReceivesDriverLocation_shouldSaveAndSendEvent() {
        // Arrange
        UpdateLocationRequest request = new UpdateLocationRequest();
        request.setDriverId(1L);
        request.setLatitude(3.5f);
        request.setLongitude(2.5f);

        // Act
        driverLocationService.updateCurrentLocation(request);

        // Assert
        verify(repository, times(1)).save(any(DriverLocationHistory.class));
        verify(kafkaTemplate, times(1)).send(anyString(), any(DriverLocationEvent.class));
    }
}