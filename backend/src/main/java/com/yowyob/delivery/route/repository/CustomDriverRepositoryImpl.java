package com.yowyob.delivery.route.repository;

import com.yowyob.delivery.route.domain.entity.DeliveryDriver;
import com.yowyob.delivery.route.domain.enums.DriverState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomDriverRepositoryImpl implements CustomDriverRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Flux<DeliveryDriver> findAllWithLocation() {
        String sql = """
                SELECT
                    id,
                    first_name,
                    last_name,
                    phone_number,
                    email,
                    license_number,
                    current_state,
                    ST_AsText(current_location) as current_location,
                    rating,
                    total_deliveries,
                    vehicle_id,
                    last_location_update,
                    is_active,
                    created_at,
                    updated_at
                FROM drivers
                """;

        return databaseClient.sql(sql)
                .map((row, metadata) -> {
                    String stateStr = row.get("current_state", String.class);
                    DriverState state;
                    try {
                        state = stateStr != null ? DriverState.valueOf(stateStr) : null;
                    } catch (IllegalArgumentException e) {
                        state = null;
                    }

                    return DeliveryDriver.builder()
                            .id(row.get("id", UUID.class))
                            .firstName(row.get("first_name", String.class))
                            .lastName(row.get("last_name", String.class))
                            .phoneNumber(row.get("phone_number", String.class))
                            .email(row.get("email", String.class))
                            .licenseNumber(row.get("license_number", String.class))
                            .currentState(state)
                            .currentLocation(row.get("current_location", String.class))
                            .rating(row.get("rating", Double.class))
                            .totalDeliveries(row.get("total_deliveries", Integer.class))
                            .vehicleId(row.get("vehicle_id", UUID.class))
                            .lastLocationUpdate(row.get("last_location_update", LocalDateTime.class))
                            .isActive(row.get("is_active", Boolean.class))
                            .createdAt(row.get("created_at", LocalDateTime.class))
                            .updatedAt(row.get("updated_at", LocalDateTime.class))
                            .build();
                })
                .all();
    }
}
