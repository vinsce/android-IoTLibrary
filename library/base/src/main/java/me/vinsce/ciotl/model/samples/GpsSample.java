package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.GpsData;

/**
 * A Gps Sample contains a {@link GpsData} instance as data
 *
 * @since 1.0.0
 */
public class GpsSample extends Sample<GpsData> {
    public GpsSample(Timestamp timestamp, GpsData data) {
        super(timestamp, data);
    }

    public GpsSample(GpsData data) {
        super(data);
    }
}