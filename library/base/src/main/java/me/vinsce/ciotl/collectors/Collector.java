package me.vinsce.ciotl.collectors;

import java.io.Closeable;

import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base interface for data collectors.
 *
 * @param <S> Type of sample created
 * @param <T> Type of data collected
 * @since 1.0.0
 */
public interface Collector<S extends Sample<T>, T extends Data> extends Closeable {
    /**
     * Returns true if the collector source (eg. sensor) is available and usable in the device.
     * False otherwise
     *
     * @return true if the data source is available, false otherwise
     */
    boolean isSourceAvailable();

    /**
     * Check if the collector is properly initialized
     *
     * @return true if the collector is properly initialized, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isInitialized();

    /**
     * Initialize the collector
     */
    void initialize();

    /**
     * Start collecting new data
     */
    void start();

    /**
     * Stop data collection
     */
    void stop();

    /**
     * Add a new listener to the collector
     *
     * @param listener the listener to be added
     */
    void addListener(CollectorListener<Sample<T>> listener);

    /**
     * Remove a listener from the collector
     *
     * @param listener the lister to be removed
     * @return true if the listener was registered in this collector
     */
    boolean removeListener(CollectorListener<Sample<T>> listener);
}