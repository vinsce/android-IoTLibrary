package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;
import android.hardware.Sensor;

import me.vinsce.ciotl.collectors.LightCollector;

public class LightCollectorConfiguration extends AndroidSensorCollectorConfiguration {

    public LightCollectorConfiguration(int samplingPeriod, int maxAllowedSamplingPeriod) {

        super("Light", Sensor.TYPE_LIGHT, samplingPeriod, maxAllowedSamplingPeriod);
    }

    public LightCollectorConfiguration(int samplingPeriod) {
        this(samplingPeriod, samplingPeriod);
    }

    @Override
    public LightCollector createCollector(Context context) {
        return new LightCollector(context, this);
    }
}
