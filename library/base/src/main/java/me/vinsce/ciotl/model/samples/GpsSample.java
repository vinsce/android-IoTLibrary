package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.GpsData;

/**
 * A Gps Sample contains a {@link GpsData} instance as data
 *
 * @since 1.0.0
 */
public class GpsSample extends Sample<GpsData> {
    private static final String TYPE = "accelerometer";

    public GpsSample(Timestamp timestamp, GpsData data, String device) {
        super(timestamp, data, device, TYPE);
    }

    public GpsSample(GpsData data, String device) {
        super(data, device, TYPE);
    }
}