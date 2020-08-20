package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;
import android.hardware.Sensor;

import me.vinsce.ciotl.collectors.AccelerometerCollector;

public class AccelerometerCollectorConfiguration extends AndroidSensorCollectorConfiguration {
    public AccelerometerCollectorConfiguration(int sensorType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super("Accelerometer", sensorType, samplingPeriod, maxAllowedSamplingPeriod);
    }

    public AccelerometerCollectorConfiguration(int samplingPeriod, int maxAllowedSamplingPeriod) {
        this(Sensor.TYPE_ACCELEROMETER, samplingPeriod, maxAllowedSamplingPeriod);
    }

    public AccelerometerCollectorConfiguration(int samplingPeriod) {
        this(samplingPeriod, samplingPeriod);
    }

    @Override
    public AccelerometerCollector createCollector(Context context) {
        return new AccelerometerCollector(context, this);
    }
}
