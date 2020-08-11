package me.vinsce.ciotl.encoders;

import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base encoder interface.
 * An encoder receives a {@link Sample} and returns the encoded value.
 *
 * @param <O> type of output
 * @since 1.0.0
 */
public interface Encoder<O> {
    /**
     * @param sample sample to be encoded
     * @param <T>    type of data contained in the sample
     * @return the encoded sample as instance of {@link O}
     */
    <T extends Data> O encode(Sample<T> sample);
}