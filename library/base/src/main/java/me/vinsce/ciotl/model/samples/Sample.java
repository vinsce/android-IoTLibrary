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

    /**
     * Create a new Sample with the current timestamp and the specified data
     *
     * @param data measured data
     */
    public Sample(T data) {
        this(new Timestamp(System.currentTimeMillis()), data);
    }

    /**
     * Create a new Sample
     *
     * @param timestamp timestamp of measure
     * @param data      measured data
     */
    public Sample(Timestamp timestamp, T data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + timestamp + "] = " + data;
    }
}