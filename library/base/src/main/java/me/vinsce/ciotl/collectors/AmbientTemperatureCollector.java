package me.vinsce.ciotl.collectors;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import me.vinsce.ciotl.model.samples.AmbientTemperatureSample;
import me.vinsce.ciotl.model.sensors.AmbientTemperatureData;

/**
 * Ambient (room) temperature data collector
 *
 * @since 1.0.0
 */
public class AmbientTemperatureCollector extends AndroidSensorAbstractCollector<AmbientTemperatureSample, AmbientTemperatureData> {

    /**
     * Create a new AmbientTemperatureCollector using the given sampling period
     *
     * @param context        Context used to access system sensors
     * @param samplingPeriod sensor sampling period, in ms
     */
    public AmbientTemperatureCollector(Context context, int samplingPeriod) {
        super(context, Sensor.TYPE_AMBIENT_TEMPERATURE, samplingPeriod, samplingPeriod);
    }

    /**
     * Create a new AmbientTemperatureCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public AmbientTemperatureCollector(Context context, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, Sensor.TYPE_AMBIENT_TEMPERATURE, samplingPeriod, maxAllowedSamplingPeriod);
    }

    /**
     * Create a new AmbientTemperatureCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public AmbientTemperatureCollector(Context context, int accelerometerType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context, accelerometerType, samplingPeriod, maxAllowedSamplingPeriod);
        checkType(accelerometerType, Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    @Override
    protected AmbientTemperatureSample processEvent(SensorEvent event) {
        AmbientTemperatureData data = new AmbientTemperatureData(event.values[0]);
        return new AmbientTemperatureSample(data, deviceIdProvider.getDeviceId());
    }
}
