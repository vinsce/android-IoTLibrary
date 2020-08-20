package me.vinsce.ciotl.mqtt.exporters.configurations;

import android.content.Context;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.configurations.ExporterConfiguration;
import me.vinsce.ciotl.mqtt.exporters.MqttExporter;

@Getter
@Setter
public class MqttExporterConfiguration extends ExporterConfiguration {
    private final String serverUri;
    private final String clientId;
    private final String topic;

    public MqttExporterConfiguration(String serverUri, String clientId, String topic) {
        super("Mqtt");
        this.serverUri = serverUri;
        this.clientId = clientId;
        this.topic = topic;
    }

    @Override
    public MqttExporter createExporter(Context context, Encoder encoder) {
        return new MqttExporter(encoder, context, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "MqttExporterConfiguration{" +
                "serverUri='" + serverUri + '\'' +
                ", clientId='" + clientId + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
