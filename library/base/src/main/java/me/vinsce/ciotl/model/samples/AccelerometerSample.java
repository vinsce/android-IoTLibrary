package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.AccelerometerData;

/**
 * An Accelerometer Sample contains a {@link AccelerometerData} instance as data
 *
 * @since 1.0.0
 */
public class AccelerometerSample extends Sample<AccelerometerData> {
    public AccelerometerSample(Timestamp timestamp, AccelerometerData data) {
        super(timestamp, data);
    }

    public AccelerometerSample(AccelerometerData data) {
        super(data);
    }
}