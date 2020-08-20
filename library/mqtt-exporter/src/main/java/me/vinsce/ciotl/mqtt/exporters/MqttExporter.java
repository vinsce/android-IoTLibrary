package me.vinsce.ciotl.mqtt.exporters;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.AbstractExporter;
import me.vinsce.ciotl.mqtt.exporters.configurations.MqttExporterConfiguration;

/**
 * An exporter that writes data to an MQTT topic as byte array
 */
public class MqttExporter extends AbstractExporter<MqttExporterConfiguration, byte[]> {
    private static final String LOG_TAG = MqttExporter.class.getSimpleName();

    private final Context context;

    private MqttAndroidClient mqttClient;

    public MqttExporter(Encoder<byte[]> encoder, Context context, String serverUri, String clientId, String topic) {
        this(encoder, context, new MqttExporterConfiguration(serverUri, clientId, topic));
    }

    /**
     * Create a new MqttExporter with the specified encoder and configuration
     *
     * @param encoder       the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     * @param context       Android context
     * @param configuration the encoder configuration
     */
    public MqttExporter(Encoder<byte[]> encoder, Context context, MqttExporterConfiguration configuration) {
        super(encoder, configuration);
        this.context = context;
    }

    @Override
    public void initialize() {
        mqttClient = new MqttAndroidClient(context, configuration.getServerUri(), configuration.getClientId());
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.i(LOG_TAG, "Reconnected to MQTT server: " + serverURI);
                } else {
                    Log.i(LOG_TAG, "Connected to MQTT server: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable t) {
                Log.i(LOG_TAG, "MQTT connection lost", t);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.i(LOG_TAG, "Received MQTT message: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.v(LOG_TAG, "MQTT delivery complete for message id " + token.getMessageId());
            }
        });

        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            Log.i(LOG_TAG, "Connecting to " + configuration.getServerUri());
            mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(500);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(true);
                    mqttClient.setBufferOpts(disconnectedBufferOptions);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(LOG_TAG, "Failed to connect to: " + configuration.getServerUri(), exception);
                    exception.printStackTrace();
                }
            });
            this.initialized = true;
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void exportEncoded(byte[] encodedSample) {
        try {
            final MqttMessage message = new MqttMessage();
            message.setPayload(encodedSample);

            Log.v(LOG_TAG, String.format("Publishing message to topic %s", configuration.getTopic()));
            mqttClient.publish(configuration.getTopic(), message);

            if (!mqttClient.isConnected()) {
                Log.i(LOG_TAG, "Message published but MQTT client not connected");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Publishing: " + e.getMessage(), e);
        }
    }
}
