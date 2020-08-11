package me.vinsce.ciotl.collectors;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;
import me.vinsce.ciotl.helpers.id.DeviceIdProvider;
import me.vinsce.ciotl.helpers.id.GeneratedUUIDProvider;
import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * Base abstract {@link Collector} with a basic implementation of listeners management.
 *
 * @param <S> Type of sample created
 * @param <T> Type of data collected
 * @since 1.0.0
 */
public abstract class AbstractCollector<S extends Sample<T>, T extends Data> implements Collector<S, T> {
    /**
     * A shared Handler that can be used by subclasses
     */
    protected static final Handler SHARED_HANDLER;

    protected final String LOG_TAG = getClass().getSimpleName();
    protected Set<CollectorListener<Sample<T>>> listeners = new HashSet<>();

    static {
        final HandlerThread handlerThread = new HandlerThread("SharedCollectorHandlerThread", Thread.MAX_PRIORITY);
        handlerThread.start();
        SHARED_HANDLER = new Handler(handlerThread.getLooper());
    }

    protected final Context context;

    @Setter
    protected DeviceIdProvider deviceIdProvider;

    public AbstractCollector(@NonNull Context context) {
        this.context = context;
        deviceIdProvider = new GeneratedUUIDProvider(context);
    }

    @Override
    public void addListener(CollectorListener<Sample<T>> listener) {
        this.listeners.add(listener);
    }

    protected void notifyListeners(Sample<T> sample) {
        for (CollectorListener<Sample<T>> listener : listeners) {
            listener.onValueCollected(sample);
        }
    }

    @Override
    public boolean removeListener(CollectorListener<Sample<T>> listener) {
        return this.listeners.remove(listener);
    }

    @Override
    public void close() {
        stop();
    }
}
