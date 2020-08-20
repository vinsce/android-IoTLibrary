package me.vinsce.ciotl.exporters.configurations;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import lombok.Getter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.LogExporter;

@Getter
public class LogExporterConfiguration extends ExporterConfiguration {
    private final String tag;
    private final int level;

    public LogExporterConfiguration() {
        this(LogExporter.class.getSimpleName(), Log.DEBUG);
    }

    public LogExporterConfiguration(String tag, int level) {
        super("Logcat");
        this.tag = tag;
        this.level = level;
    }

    @Override
    public LogExporter createExporter(Context context, Encoder encoder) {
        return new LogExporter(encoder, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "LogExporterConfiguration{" +
                "tag='" + tag + '\'' +
                ", level=" + level +
                '}';
    }
}
