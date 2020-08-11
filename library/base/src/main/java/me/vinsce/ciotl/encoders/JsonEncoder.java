package me.vinsce.ciotl.encoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.vinsce.ciotl.model.samples.Sample;
import me.vinsce.ciotl.model.sensors.Data;

/**
 * An encoder that creates a JSON representation of the sample using Gson
 * and return the json value as {@link String}
 *
 * @since 1.0.0
 */
public class JsonEncoder implements Encoder<String> {
    private final Gson GSON;

    /**
     * Create a new JsonEncoder using the specified GsonBuilder
     *
     * @param gsonBuilder GsonBuilder used to create the Gson instance
     */
    public JsonEncoder(GsonBuilder gsonBuilder) {
        this.GSON = gsonBuilder.create();
    }

    /**
     * Create a new encoder with the standard Gson configuration
     */
    public JsonEncoder() {
        GSON = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")
                .create();
    }

    @Override
    public <T extends Data> String encode(Sample<T> sample) {
        return GSON.toJson(sample);
    }
}