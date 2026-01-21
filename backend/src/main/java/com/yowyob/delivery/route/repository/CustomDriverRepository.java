package com.yowyob.delivery.route.repository;

import com.yowyob.delivery.route.domain.entity.DeliveryDriver;
import reactor.core.publisher.Flux;

/**
 * Custom repository for delivery driver specific queries.
 */
public interface CustomDriverRepository {
    /**
     * Find all drivers with their location converted to WKT.
     */
    Flux<DeliveryDriver> findAllWithLocation();
}
