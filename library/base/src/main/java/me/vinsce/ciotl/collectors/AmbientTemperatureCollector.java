package me.vinsce.ciotl.collectors;


import android.content.Context;
import android.hardware.SensorEvent;

import me.vinsce.ciotl.collectors.configurations.AmbientTemperatureCollectorConfiguration;
import me.vinsce.ciotl.model.samples.AmbientTemperatureSample;
import me.vinsce.ciotl.model.sensors.AmbientTemperatureData;

/**
 * Ambient (room) temperature data collector
 *
 * @since 1.0.0
 */
public class AmbientTemperatureCollector extends AndroidSensorAbstractCollector<AmbientTemperatureCollectorConfiguration, AmbientTemperatureSample, AmbientTemperatureData> {

    /**
     * Create a new AmbientTemperatureCollector using the given sampling period and max allowed sampling period
     *
     * @param context                  Context used to access system sensors
     * @param samplingPeriod           sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod maximum allowed sensor sampling period, in ms
     */
    public AmbientTemperatureCollector(Context context, int samplingPeriod, int maxAllowedSamplingPeriod) {
        this(context, new AmbientTemperatureCollectorConfiguration(samplingPeriod, maxAllowedSamplingPeriod));
    }

    /**
     * Create a new AmbientTemperatureCollector using the given sampling period
     *
     * @param context        Context used to access system sensors
     * @param samplingPeriod sensor sampling period, in ms
     */
    public AmbientTemperatureCollector(Context context, int samplingPeriod) {
        this(context, new AmbientTemperatureCollectorConfiguration(samplingPeriod));
    }

    public AmbientTemperatureCollector(Context context, AmbientTemperatureCollectorConfiguration configuration) {
        super(context, configuration);
    }

    @Override
    protected AmbientTemperatureSample processEvent(SensorEvent event) {
        AmbientTemperatureData data = new AmbientTemperatureData(event.values[0]);
        return new AmbientTemperatureSample(data, deviceIdProvider.getDeviceId());
    }
}
