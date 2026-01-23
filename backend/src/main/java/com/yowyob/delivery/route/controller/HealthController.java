package com.yowyob.delivery.route.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for simple health monitoring entries.
 */
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Monitor service availability")
public class HealthController {

    @GetMapping
    @Operation(summary = "Health check", description = "Returns the current status of the route service.")
    public Mono<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "route-service");
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }
}
