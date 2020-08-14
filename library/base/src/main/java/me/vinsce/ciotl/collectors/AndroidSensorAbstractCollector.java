package me.vinsce.ciotl.collectors;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Arrays;

import me.vinsce.ciotl.exceptions.InitializationException;
import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Abstract base implementation of a collector that uses and android sensor obtained from {@link SensorManager}
 *
 * @since 1.0.0
 */
public abstract class AndroidSensorAbstractCollector<S extends Sample<T>, T extends Data> extends AbstractCollector<S, T> implements SensorEventListener {
    private static final int MS_TO_US_CONVERSION_FACTOR = 1000;

    private boolean initialized;

    private SensorManager sensorManager;
    private Sensor sensor;

    protected int sensorType;

    private long lastNotificationTs;

    protected final int samplingPeriod; // in milliseconds
    protected final int maxAllowedSamplingPeriod; // in milliseconds

    /**
     * @param context                   Context used to access system sensors
     * @param sensorType                type of sensor. A constant from the {@link Sensor} class
     * @param samplingPeriod            the sensor sampling period, in ms
     * @param maxAllowedSamplingPeriod, the maximum allowed sensor sampling period, in ms
     */
    public AndroidSensorAbstractCollector(Context context, int sensorType, int samplingPeriod, int maxAllowedSamplingPeriod) {
        super(context);
        this.sensorType = sensorType;
        this.samplingPeriod = samplingPeriod;
        this.maxAllowedSamplingPeriod = maxAllowedSamplingPeriod;
    }


    @Override
    public void initialize() {
        if (sensorManager == null)
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensor == null)
            sensor = sensorManager.getDefaultSensor(sensorType);

        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public boolean isSourceAvailable() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        return sensor != null;
    }

    @Override
    public void start() {
        if (!isSourceAvailable())
            throw new RuntimeException(String.format("Sensor of type %d not available", sensorType));
        if (!isInitialized())
            initialize();
        if (!isInitialized())
            throw new RuntimeException("Unable to initialize sensor collector");

        sensorManager.registerListener(this, sensor, samplingPeriod * MS_TO_US_CONVERSION_FACTOR, SHARED_HANDLER);
    }

    @Override
    public void stop() {
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        long now = System.currentTimeMillis();
        if (now - lastNotificationTs >= maxAllowedSamplingPeriod) {
            S sample = processEvent(event);
            notifyListeners(sample);
            lastNotificationTs = now;
        }
    }

    abstract protected S processEvent(SensorEvent event);

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    protected void checkType(int type, int... allowedTypes) {
        for (int allowedType : allowedTypes) {
            if (type == allowedType)
                return;
        }
        throw new InitializationException(String.format("Sensor type %d not supported from collector of type %s. Allowed values are: %s", type, getClass().getSimpleName(), Arrays.toString(allowedTypes)));
    }
}
