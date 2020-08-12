package me.vinsce.ciotl.collectors;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import me.vinsce.ciotl.model.samples.LightSample;
import me.vinsce.ciotl.model.sensors.LightData;

/**
 * Light sensor data collector
 *
 * @since 1.0.0
 */
public class LightCollector extends AndroidSensorAbstractCollector<LightSample, LightData> {

    /**
     * Create a new LightCollector using the given sampling period
     *
     * @param context        Context used to access system sensors
     * @param samplingPeriod sensor sampling period, in ms
     */
    public LightCollector(Context context, int samplingPeriod) {
        super(context, Sensor.TYPE_LIGHT, samplingPeriod, samplingPeriod);
    }

    /**
     * Create a new LightCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public LightCollector(Context context, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, Sensor.TYPE_LIGHT, samplingPeriod, maxAllowedSamplingPeriod);
    }

    /**
     * Create a new LightCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public LightCollector(Context context, int accelerometerType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, accelerometerType, samplingPeriod, maxAllowedSamplingPeriod);
        checkType(accelerometerType, Sensor.TYPE_LIGHT);
    }

    @Override
    protected LightSample processEvent(SensorEvent event) {
        LightData data = new LightData(event.values[0]);
        return new LightSample(data, deviceIdProvider.getDeviceId());
    }
}
