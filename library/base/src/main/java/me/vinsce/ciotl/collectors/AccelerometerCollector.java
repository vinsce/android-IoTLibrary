package me.vinsce.ciotl.collectors;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.sql.Time;
import java.sql.Timestamp;

import me.vinsce.ciotl.model.samples.AccelerometerSample;
import me.vinsce.ciotl.model.sensors.AccelerometerData;

/**
 * Accelerometer sensor data collector.
 * Collects acceleration value in the three directions into an {@link AccelerometerSample}
 *
 * @since 1.0.0
 */
public class AccelerometerCollector extends AndroidSensorAbstractCollector<AccelerometerSample, AccelerometerData> {

    /**
     * Create a new AccelerometerCollector using the given sampling period
     *
     * @param context        Context used to access system sensors
     * @param samplingPeriod sensor sampling period, in ms
     */
    public AccelerometerCollector(Context context, int samplingPeriod) {
        super(context, Sensor.TYPE_ACCELEROMETER, samplingPeriod, samplingPeriod);
    }

    /**
     * Create a new AccelerometerCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public AccelerometerCollector(Context context, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, Sensor.TYPE_ACCELEROMETER, samplingPeriod, maxAllowedSamplingPeriod);
    }

    /**
     * Create a new AccelerometerCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public AccelerometerCollector(Context context, int accelerometerType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, accelerometerType, samplingPeriod, maxAllowedSamplingPeriod);
        checkType(accelerometerType, Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    protected AccelerometerSample processEvent(SensorEvent event) {
        AccelerometerData data = new AccelerometerData(event.values[0], event.values[1], event.values[2]);
        return new AccelerometerSample(data);
    }
}
