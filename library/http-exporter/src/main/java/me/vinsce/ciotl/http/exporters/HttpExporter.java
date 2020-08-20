package me.vinsce.ciotl.http.exporters;

import android.util.Log;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NoCache;

import me.vinsce.ciotl.encoders.Encoder;
import me.vinsce.ciotl.exceptions.InitializationException;
import me.vinsce.ciotl.exporters.AbstractExporter;
import me.vinsce.ciotl.http.exporters.configurations.HttpExporterConfiguration;


/**
 * An exporter that writes data to an HTTP endpoint as String
 */
public class HttpExporter extends AbstractExporter<HttpExporterConfiguration, String> {
    private static final String LOG_TAG = HttpExporter.class.getSimpleName();
    private static final String REQUEST_TAG = "HttpExporter";

    private RequestQueue requestQueue;

    /**
     * Create a new HttpExporter with the specified Encoder, method and endpoint
     *
     * @param encoder the encoder used to encode samples before export
     * @param method  HTTP method from {@link Request.Method}
     * @param url     HTTP endpoint for the request
     */
    public HttpExporter(Encoder<String> encoder, int method, String url) {
        this(encoder, new HttpExporterConfiguration(url, method));
    }

    /**
     * Create a new HttpExporter with the specified Encoder and request builder
     *
     * @param encoder        the encoder used to encode samples before export
     * @param requestBuilder custom request builder
     */
    public HttpExporter(Encoder<String> encoder, HttpRequestBuilder<String, ?> requestBuilder) {
        this(encoder, new HttpExporterConfiguration(requestBuilder));
    }

    /**
     * Create a new HttpExporter with the specified encoder and configuration
     *
     * @param encoder       the encoder used to encode values before export (eg. {@link me.vinsce.ciotl.encoders.JsonEncoder})
     * @param configuration the encoder configuration
     */
    public HttpExporter(Encoder<String> encoder, HttpExporterConfiguration configuration) {
        super(encoder, configuration);
    }

    @Override
    public void exportEncoded(String encodedSample) {
        final Request<?> request;

        if (configuration.getRequestBuilder() != null)
            request = configuration.getRequestBuilder().buildRequest(encodedSample);
        else
            request = new JsonRequest<NetworkResponse>(configuration.getMethod(), configuration.getUrl(), encodedSample, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    Log.v(LOG_TAG, "onResponse: " + response.statusCode);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w(LOG_TAG, "onErrorResponse", error);
                }
            }) {
                @Override
                protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200)
                        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                    else return Response.error(new VolleyError(response));
                }
            };

        requestQueue.add(request);
    }

    @Override
    public void close() {
        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG);
            requestQueue.stop();
        }
    }

    @Override
    public void initialize() throws InitializationException {
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(new NoCache(), network);
        requestQueue.start();
        this.initialized = true;
    }
}
