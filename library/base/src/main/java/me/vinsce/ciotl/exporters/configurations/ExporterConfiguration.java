package me.vinsce.ciotl.exporters.configurations;

import android.content.Context;

import lombok.Getter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.Exporter;

/**
 * @since 1.0.0
 */
@Getter
public abstract class ExporterConfiguration {
    private final String exporterName;

    public ExporterConfiguration(String exporterName) {
        this.exporterName = exporterName;
    }

    public abstract Exporter<?> createExporter(Context context, Encoder encoder);
}