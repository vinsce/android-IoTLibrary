package me.vinsce.ciotl.encoders;

import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * An encoder that creates a JSON representation of the sample using Gson
 * and return the json value as byte array
 *
 * @since 1.0.0
 */
public class JsonByteEncoder implements Encoder<byte[]> {
    private JsonEncoder jsonEncoder;

    /**
     * Create a new JsonByteEncoder using the specified GsonBuilder
     *
     * @param gsonBuilder GsonBuilder used to create the Gson instance
     */
    public JsonByteEncoder(GsonBuilder gsonBuilder) {
        jsonEncoder = new JsonEncoder(gsonBuilder);
    }

    /**
     * Create a new JsonByteEncoder with the standard Gson configuration
     */
    public JsonByteEncoder() {
        jsonEncoder = new JsonEncoder();
    }

    @Override
    public <T extends Data> byte[] encode(Sample<T> sample) {
        return jsonEncoder.encode(sample).getBytes(StandardCharsets.UTF_8);
    }
}