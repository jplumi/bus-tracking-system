package com.jplumi.location.controller;

import com.jplumi.location.dto.UpdateLocationRequest;
import com.jplumi.location.service.DriverLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location/driver")
@RequiredArgsConstructor
public class DriverLocationController {

    private final DriverLocationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCurrentLocation(@RequestBody UpdateLocationRequest request) {
        service.updateCurrentLocation(request);
    }

}
