package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;
import android.hardware.Sensor;

import me.vinsce.ciotl.collectors.AmbientTemperatureCollector;

public class AmbientTemperatureCollectorConfiguration extends AndroidSensorCollectorConfiguration {

    public AmbientTemperatureCollectorConfiguration(int samplingPeriod, int maxAllowedSamplingPeriod) {

        super("AmbientTemperature", Sensor.TYPE_AMBIENT_TEMPERATURE, samplingPeriod, maxAllowedSamplingPeriod);
    }

    public AmbientTemperatureCollectorConfiguration(int samplingPeriod) {
        this(samplingPeriod, samplingPeriod);
    }

    @Override
    public AmbientTemperatureCollector createCollector(Context context) {
        return new AmbientTemperatureCollector(context, this);
    }
}
