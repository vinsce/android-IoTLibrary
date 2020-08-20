package me.vinsce.ciotl.exporters;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exceptions.InitializationException;
import me.vinsce.ciotl.exceptions.NotInitializedException;
import me.vinsce.ciotl.exporters.configurations.ExporterConfiguration;
import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base abstract implementation of Exporter that takes care of encoding
 * and demands the export of the encoded value to the subclass
 *
 * @param <I> type of encoded value, ready to be exported
 * @since 1.0.0
 */
public abstract class AbstractExporter<C extends ExporterConfiguration, I> implements Exporter<C> {
    protected Encoder<I> encoder;
    protected boolean initialized = false;

    @Getter
    protected final C configuration;

    @Getter
    @Setter
    protected boolean useGenericSamples = false;

    /**
     * Create a new Exporter with the specified Encoder
     *
     * @param encoder       the encoder used to encode samples before export
     * @param configuration the exporter configuration
     */
    public AbstractExporter(@NonNull Encoder<I> encoder, C configuration) {
        this.encoder = encoder;
        this.configuration = configuration;
    }

    @Override
    public <T extends Data> void export(Sample<T> sample) throws NotInitializedException {
        if (!initialized)
            throw new NotInitializedException("Exporter not initialized");
        if (encoder == null)
            throw new NotInitializedException("Encoder not configured for Exporter");
        if (useGenericSamples)
            exportEncoded(encoder.encode(sample.toGenericSample()));
        else
            exportEncoded(encoder.encode(sample));
    }

    public abstract void exportEncoded(I encodedSample);

    @Override
    public void initialize() throws InitializationException {
        this.initialized = true;
    }
}
