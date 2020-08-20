package me.vinsce.ciotl.exporters;

import android.util.Log;

import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.configurations.LogExporterConfiguration;

/**
 * An implementation of Exporter for debugging.
 * Receives an encoded samples and logs it in Logcat
 * with the specified {@link #configuration#tag} and {@link #configuration#level}
 *
 * @since 1.0.0
 */
public class LogExporter extends AbstractExporter<LogExporterConfiguration, String> {

    /**
     * Create a new LogExporter with the default log tag (class name) and level (debug)
     * and the specified Encoder
     *
     * @param encoder the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     */
    public LogExporter(Encoder<String> encoder) {
        this(encoder, new LogExporterConfiguration());
    }

    /**
     * Create a new LogExporter with the specified log level and tag
     *
     * @param encoder the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     * @param tag     Log tag using for the export
     * @param level   Log level used for the export
     */
    public LogExporter(Encoder<String> encoder, String tag, int level) {
        this(encoder, new LogExporterConfiguration(tag, level));
    }

    /**
     * Create a new LogExporter with the specified encoder and configuration
     *
     * @param encoder       the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     * @param configuration the encoder configuration
     */
    public LogExporter(Encoder<String> encoder, LogExporterConfiguration configuration) {
        super(encoder, configuration);
    }

    @Override
    public void exportEncoded(String encodedSample) {
        switch (configuration.getLevel()) {
            case Log.VERBOSE:
                Log.v(configuration.getTag(), encodedSample);
                break;
            case Log.DEBUG:
                Log.d(configuration.getTag(), encodedSample);
                break;
            case Log.INFO:
                Log.i(configuration.getTag(), encodedSample);
                break;
            case Log.WARN:
                Log.w(configuration.getTag(), encodedSample);
                break;
            case Log.ERROR:
                Log.e(configuration.getTag(), encodedSample);
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid log level %d", configuration.getLevel()));
        }
    }

    @Override
    public void initialize() {
        this.initialized = true;
    }

    @Override
    public void close() {}
}