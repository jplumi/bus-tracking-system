package com.jplumi.passenger.controller;

import com.jplumi.passenger.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{passengerId}/location")
    public SseEmitter addLocationListener(@PathVariable Long passengerId, @RequestParam Long tripId) {
        return locationService.addLocationListener(tripId, passengerId);
    }
}
