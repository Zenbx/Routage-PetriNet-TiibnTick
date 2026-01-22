package com.yowyob.delivery.route.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

/**
 * Convertisseurs pour les types géométriques PostGIS (JTS)
 * Nécessaires pour Spring Data R2DBC avec PostGIS
 */
public class GeometryConverters {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    /**
     * Convertit un Point JTS (org.locationtech.jts.geom.Point) en String WKT
     * Utilisé quand Spring Data R2DBC lit les données depuis PostgreSQL/PostGIS
     */
    @ReadingConverter
    public static class JtsPointToStringConverter implements Converter<Point, String> {
        @Override
        public String convert(@NonNull Point source) {
            // Format WKT: POINT(longitude latitude)
            return String.format("POINT(%s %s)", source.getX(), source.getY());
        }
    }
}