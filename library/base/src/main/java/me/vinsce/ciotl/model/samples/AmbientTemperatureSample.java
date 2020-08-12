package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.AccelerometerData;
import me.vinsce.ciotl.model.sensors.AmbientTemperatureData;

/**
 * An AmbientTemperature Sample contains a {@link AmbientTemperatureData} instance as data
 *
 * @since 1.0.0
 */
public class AmbientTemperatureSample extends Sample<AmbientTemperatureData> {
    private static final String TYPE = "temperature";

    public AmbientTemperatureSample(Timestamp timestamp, AmbientTemperatureData data, String device) {
        super(timestamp, data, device, TYPE);
    }

    public AmbientTemperatureSample(AmbientTemperatureData data, String device) {
        super(data, device, TYPE);
    }
}