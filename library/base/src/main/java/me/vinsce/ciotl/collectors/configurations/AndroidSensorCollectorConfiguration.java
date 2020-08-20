package me.vinsce.ciotl.collectors.configurations;

import android.hardware.Sensor;

import androidx.annotation.NonNull;

import lombok.Getter;

@Getter
public abstract class AndroidSensorCollectorConfiguration extends CollectorConfiguration {

    protected int samplingPeriod; // in milliseconds

    protected int maxAllowedSamplingPeriod; // in milliseconds

    protected int sensorType;

    /**
     * @param collectorName            name of the collector
     * @param sensorType               type of sensor. A constant from the {@link Sensor} class
     * @param samplingPeriod           sampling period in ms
     * @param maxAllowedSamplingPeriod max allowed sampling period in ms
     */
    public AndroidSensorCollectorConfiguration(String collectorName, int sensorType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(collectorName);
        this.sensorType = sensorType;
        this.samplingPeriod = samplingPeriod;
        this.maxAllowedSamplingPeriod = maxAllowedSamplingPeriod;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "samplingPeriod=" + samplingPeriod +
                ", maxAllowedSamplingPeriod=" + maxAllowedSamplingPeriod +
                ", sensorType=" + sensorType +
                '}';
    }
}