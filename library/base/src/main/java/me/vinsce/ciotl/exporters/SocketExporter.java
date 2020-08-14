package me.vinsce.ciotl.exporters;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exceptions.InitializationException;

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
    private byte[] startSequence;
    @Setter
    private byte[] endSequence;

    @Setter
    private int timeout = 5000;

    private Socket socket;
    private OutputStream outputStream;

    /**
     * Create a new SocketExporter in Client mode
     *
     * @param encoder encoder used to encode samples before export
     * @param host    server host
     * @param port    server port
     */
    public SocketExporter(Encoder<byte[]> encoder, String host, int port) {
        super(encoder);
        this.port = port;
        this.host = host;
    }

    /**
     * Create a new SocketExporter in Server mode.
     * Note: the initialization blocks until a client is connected and only one client is supported.
     *
     * @param encoder encoder used to encode samples before export
     * @param port    port to bind to
     */
    public SocketExporter(Encoder<byte[]> encoder, int port) {
        super(encoder);
        this.port = port;
        this.host = null;
    }

    @Override
    public void exportEncoded(byte[] encodedSample) {
        try {
            if (startSequence != null && startSequence.length > 0)
                outputStream.write(startSequence);

            outputStream.write(encodedSample);

            if (endSequence != null && endSequence.length > 0)
                outputStream.write(endSequence);

            outputStream.flush();
        } catch (IOException e) {
            Log.e(LOG_TAG, String.format("Unable to export %s", Base64.encodeToString(encodedSample, Base64.DEFAULT)), e);
        }
    }

    @Override
    public void initialize() {
        Log.i(LOG_TAG, "Initializing SocketExporter");
        try {
            if (host == null) {
                Log.d(LOG_TAG, "Starting SocketExporter in server mode");

                // server mode
                final ServerSocket ss = new ServerSocket(port);
                Log.d(LOG_TAG, "Awaiting client connection to " + ss.getLocalSocketAddress());

                socket = ss.accept();
                Log.d(LOG_TAG, "Client connected from " + socket.getInetAddress().getHostAddress());
            } else {
                Log.d(LOG_TAG, "Starting SocketExporter in client mode");
                // client mode
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), timeout);
            }
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            throw new InitializationException("Failed to initialize SocketExporter", e);
        }
        Log.i(LOG_TAG, "Initialized SocketExporter");
        initialized = true;
    }

    @Override
    public void close() {
        Log.i(LOG_TAG, "Closing SocketExporter");
        if (!initialized) {
            Log.w(LOG_TAG, "Trying to close a non-initialized SocketExporter");
            return;
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while closing output stream", e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while closing socket", e);
        }
        socket = null;
        outputStream = null;
        initialized = false;
    }
}