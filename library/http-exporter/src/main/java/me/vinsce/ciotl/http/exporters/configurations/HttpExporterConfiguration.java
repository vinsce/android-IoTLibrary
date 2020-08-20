package me.vinsce.ciotl.http.exporters.configurations;

import android.content.Context;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exporters.configurations.ExporterConfiguration;
import me.vinsce.ciotl.http.exporters.HttpExporter;
import me.vinsce.ciotl.http.exporters.HttpRequestBuilder;

@Getter
@Setter
public class HttpExporterConfiguration extends ExporterConfiguration {
    private final HttpRequestBuilder<String, ?> requestBuilder;
    private final String url;
    private final int method;

    public HttpExporterConfiguration(HttpRequestBuilder<String, ?> requestBuilder) {
        super("Http");
        this.requestBuilder = requestBuilder;
        this.url = null;
        this.method = 0;
    }

    public HttpExporterConfiguration(String url, int method) {
        super("Http");
        this.requestBuilder = null;
        this.url = url;
        this.method = method;
    }

    @Override
    public HttpExporter createExporter(Context context, Encoder encoder) {
        return new HttpExporter(encoder, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpExporterConfiguration{" +
                "requestBuilder=" + requestBuilder +
                ", url='" + url + '\'' +
                ", method=" + method +
                '}';
    }
}
