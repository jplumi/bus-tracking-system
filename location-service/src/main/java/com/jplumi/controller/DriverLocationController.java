package com.jplumi.controller;

import com.jplumi.dto.UpdateLocationRequest;
import com.jplumi.service.DriverLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DriverLocationController {

    private final DriverLocationService service;

    @PostMapping("/driver/location")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCurrentLocation(@RequestBody UpdateLocationRequest request) {
        service.updateCurrentLocation(request);
    }

}
