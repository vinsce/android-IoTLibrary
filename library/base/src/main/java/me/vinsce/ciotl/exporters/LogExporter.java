package me.vinsce.ciotl.exporters;

import android.util.Log;

import me.vinsce.ciotl.encoders.Encoder;

/**
 * An implementation of Exporter for debugging.
 * Receives an encoded samples and logs it in Logcat
 * with the specified {@link #tag} and {@link #level}
 *
 * @since 1.0.0
 */
public class LogExporter extends AbstractExporter<String> {
    private final String tag;
    private final int level;

    /**
     * Create a new LogExporter with the default log tag (class name) and level (debug)
     * and the specified Encoder
     *
     * @param encoder the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     */
    public LogExporter(Encoder<String> encoder) {
        this(encoder, LogExporter.class.getSimpleName(), Log.DEBUG);
    }

    /**
     * Create a new LogExporter with the specified log level and tag
     *
     * @param encoder the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     * @param tag     Log tag using for the export
     * @param level   Log level used for the export
     */
    public LogExporter(Encoder<String> encoder, String tag, int level) {
        super(encoder);
        this.tag = tag;
        this.level = level;
    }

    @Override
    public void exportEncoded(String encodedSample) {
        switch (level) {
            case Log.VERBOSE:
                Log.v(tag, encodedSample);
                break;
            case Log.DEBUG:
                Log.d(tag, encodedSample);
                break;
            case Log.INFO:
                Log.i(tag, encodedSample);
                break;
            case Log.WARN:
                Log.w(tag, encodedSample);
                break;
            case Log.ERROR:
                Log.e(tag, encodedSample);
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid log level %d", level));
        }
    }

    @Override
    public void initialize() {
        this.initialized = true;
    }

    @Override
    public void close() {}
}