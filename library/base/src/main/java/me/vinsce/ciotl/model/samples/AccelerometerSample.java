package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.AccelerometerData;

/**
 * An Accelerometer Sample contains a {@link AccelerometerData} instance as data
 *
 * @since 1.0.0
 */
public class AccelerometerSample extends Sample<AccelerometerData> {
    private static final String TYPE = "accelerometer";

    public AccelerometerSample(Timestamp timestamp, AccelerometerData data, String device) {
        super(timestamp, data, device, TYPE);
    }

    public AccelerometerSample(AccelerometerData data, String device) {
        super(data, device, TYPE);
    }
}