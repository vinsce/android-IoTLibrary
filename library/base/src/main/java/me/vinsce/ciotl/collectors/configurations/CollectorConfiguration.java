package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;

import lombok.Getter;
import me.vinsce.ciotl.collectors.Collector;

/**
 * @since 1.0.0
 */
@Getter
public abstract class CollectorConfiguration {
    private final String collectorName;

    public CollectorConfiguration(String collectorName) {
        this.collectorName = collectorName;
    }

    public abstract Collector createCollector(Context context);
}