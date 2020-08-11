package me.vinsce.ciotl.collectors;

import me.vinsce.ciotl.model.samples.Sample;

/**
 * @param <T>
 * @since 1.0.0
 */
public interface CollectorListener<T extends Sample<?>> {
    /**
     * Callback method called when a new value has been collected by the {@link Collector}.
     *
     * @param sample the newly collected sample
     */
    void onValueCollected(T sample);
}