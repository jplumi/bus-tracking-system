package com.jplumi.location.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("travel-management-service")
public interface TravelManagementClient {

    @GetMapping("/drivers/validate-id")
    boolean validateDriverId(@PathVariable Long driverId);

}
