package me.vinsce.ciotl;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.vinsce.ciotl.collectors.Collector;
import me.vinsce.ciotl.exporters.Exporter;
import me.vinsce.ciotl.model.samples.Sample;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Pipeline {
    private static final String LOG_TAG = Pipeline.class.getSimpleName();

    private final Set<Collector> collectors;
    private final Set<Exporter> exporters;

    public Pipeline() {
        this.collectors = ConcurrentHashMap.newKeySet();
        this.exporters = ConcurrentHashMap.newKeySet();
    }

    public void addCollector(Collector collector) {
        collectors.add(collector);
    }

    @SafeVarargs
    public final void addCollectors(Collector... collectors) {
        this.collectors.addAll(Arrays.asList(collectors));
    }

    public void addExporter(Exporter exporter) {
        exporters.add(exporter);
    }

    public void addExporters(Exporter... exporters) {
        this.exporters.addAll(Arrays.asList(exporters));
    }

    public void onValueCollected(Sample<?> sample) {
        for (Exporter exporter : exporters) {
            exporter.export(sample);
        }
    }

    public void start() {
        for (Exporter exporter : exporters) {
            exporter.initialize();
        }
        for (Collector collector : collectors) {
            collector.addListener(this::onValueCollected);
            collector.start();
        }
    }

    public void stop() {
        for (Collector collector : collectors) {
            collector.removeListener(this::onValueCollected);
            collector.stop();
        }
        for (Exporter exporter : exporters) {
            try {
                exporter.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Failed to close exporter " + exporter.getClass().getSimpleName(), e);
            }
        }
    }
}