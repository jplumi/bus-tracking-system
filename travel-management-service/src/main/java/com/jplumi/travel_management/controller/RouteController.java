package com.jplumi.travel_management.controller;

import com.jplumi.travel_management.dto.RouteDTO;
import com.jplumi.travel_management.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService service;

    @GetMapping
    public List<RouteDTO> getAllRoutes() {
        return service.getAllRoutes();
    }

    @PostMapping
    public RouteDTO createRoute(@RequestBody RouteDTO routeDTO) {
        return service.createRoute(routeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRouteById(@PathVariable Long id) {
        service.deleteRouteById(id);
    }
}
