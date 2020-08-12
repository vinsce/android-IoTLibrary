package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.LightData;

/**
 * An Light Sample contains a {@link LightSample} instance as data
 *
 * @since 1.0.0
 */
public class LightSample extends Sample<LightData> {
    private static final String TYPE = "light";

    public LightSample(Timestamp timestamp, LightData data, String device) {
        super(timestamp, data, device, TYPE);
    }

    public LightSample(LightData data, String device) {
        super(data, device, TYPE);
    }
}