package me.vinsce.ciotl.exporters;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exceptions.InitializationException;
import me.vinsce.ciotl.exceptions.NotInitializedException;

/**
 * An implementation of Exporter that send encoded samples on a socket.
 *
 * @since 1.0.0
 */
public class SocketExporter extends AbstractExporter<byte[]> {
    private static final String LOG_TAG = SocketExporter.class.getSimpleName();

    private final int port;
    private final String host;

    @Setter
    private int timeout = 5000;

    private Socket socket;
    private OutputStream outputStream;

    public SocketExporter(Encoder<byte[]> encoder, String host, int port) {
        super(encoder);
        this.port = port;
        this.host = host;
    }

    @Override
    public void exportEncoded(byte[] encodedSample) {
        try {
            outputStream.write(encodedSample);
            outputStream.flush();
        } catch (IOException e) {
            Log.e(LOG_TAG, String.format("Unable to export %s", Base64.encodeToString(encodedSample, Base64.DEFAULT)), e);
        }
    }

    @Override
    public void initialize() {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), timeout);
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            throw new InitializationException("Failed to initialize SocketExporter", e);
        }
        initialized = true;
    }

    @Override
    public void close() {
        if (!initialized) {
            Log.w(LOG_TAG, "Trying to close a non-initialized SocketExporter");
            return;
        }

        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while closing resources", e);
        }

        initialized = false;
    }
}