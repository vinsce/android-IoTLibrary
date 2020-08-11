package me.vinsce.ciotl.model.samples;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

import lombok.Getter;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base class for data samples.
 * Contains a timestamp ({@link Timestamp} class) and the data value as instance of {@link T}
 *
 * @param <T> type of data
 * @since 1.0.0
 */
@Getter
public abstract class Sample<T extends Data> {
    private Timestamp timestamp;
    private final T data;
    private final String device;
    private final String type;

    /**
     * Create a new Sample with the current timestamp and the specified data
     *
     * @param data measured data
     */
    public Sample(T data, String device, String type) {
        this(new Timestamp(System.currentTimeMillis()), data, device, type);
    }

    /**
     * Create a new Sample
     *
     * @param timestamp timestamp of measure
     * @param data      measured data
     */
    public Sample(Timestamp timestamp, T data, String device, String type) {
        this.timestamp = timestamp;
        this.data = data;
        this.device = device;
        this.type = type;
    }

    /**
     * Create a generic sample from the current instance
     *
     * @return a GenericSample with the same information of the current sample
     */
    public GenericSample toGenericSample() {
        return new GenericSample(this);
    }

    @NonNull
    @Override
    public String toString() {
        return device + "." + type + "[" + timestamp + "] = " + data;
    }
}