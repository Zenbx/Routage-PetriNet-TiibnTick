package com.yowyob.delivery.route.repository;

import com.yowyob.delivery.route.domain.entity.Parcel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Custom repository interface for Parcel-specific queries
 * that require manual mapping (e.g., PostGIS functions).
 */
public interface CustomParcelRepository {

    /**
     * Find all parcels with locations converted to WKT format using ST_AsText.
     * Uses manual mapping to handle PostGIS geometry types.
     */
    Flux<Parcel> findAllWithLocations();

    /**
     * Find a parcel by ID with locations converted to WKT format.
     */
    Mono<Parcel> findByIdWithLocations(UUID id);

    /**
     * Save a parcel with PostGIS geometry support.
     * Converts WKT strings to PostGIS geometry using ST_GeomFromText.
     */
    Mono<Parcel> saveWithGeometry(Parcel parcel);

    /**
     * Update only the Petri Net ID of a parcel.
     * Used to avoid conversion issues with other fields during creation flow.
     */
    Mono<Integer> updatePetriNetId(UUID parcelId, String petriNetId);
}