package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.BatteryData;

/**
 * An Battery Sample contains a {@link BatterySample} instance as data
 *
 * @since 1.0.0
 */
public class BatterySample extends Sample<BatteryData> {
    private static final String TYPE = "battery";

    public BatterySample(Timestamp timestamp, BatteryData data, String device) {
        super(timestamp, data, device, TYPE);
    }

    public BatterySample(BatteryData data, String device) {
        super(data, device, TYPE);
    }
}