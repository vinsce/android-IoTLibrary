package me.vinsce.ciotl.exporters;

import java.io.Closeable;

import me.vinsce.ciotl.exceptions.InitializationException;
import me.vinsce.ciotl.exceptions.NotInitializedException;
import me.vinsce.ciotl.exporters.configurations.ExporterConfiguration;
import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base exporter interface.
 * An exporter receives a sample and sends it to the configured destination.
 *
 * @param <C> type of exporter configuration
 * @since 1.0.0
 */
public interface Exporter<C extends ExporterConfiguration> extends Closeable {
    <T extends Data> void export(Sample<T> sample) throws NotInitializedException;

    void initialize() throws InitializationException;

    C getConfiguration();
}