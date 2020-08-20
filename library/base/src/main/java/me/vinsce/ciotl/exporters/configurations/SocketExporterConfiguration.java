package me.vinsce.ciotl.exporters.configurations;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.SocketExporter;

@Getter
@Setter
public class SocketExporterConfiguration extends ExporterConfiguration {

    private final int port;
    private final String host;

    private byte[] startSequence;
    private byte[] endSequence;

    private int timeout = 5000;

    public SocketExporterConfiguration(String host, int port) {
        super("Socket");
        this.port = port;
        this.host = host;
    }

    @Override
    public SocketExporter createExporter(Context context, Encoder encoder) {
        return new SocketExporter(encoder, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "SocketExporterConfiguration{" +
                "port=" + port +
                ", host='" + host + '\'' +
                ", startSequence=" + Arrays.toString(startSequence) +
                ", endSequence=" + Arrays.toString(endSequence) +
                ", timeout=" + timeout +
                '}';
    }
}
