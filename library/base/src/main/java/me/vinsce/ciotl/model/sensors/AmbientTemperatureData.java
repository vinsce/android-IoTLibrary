package me.vinsce.ciotl.model.sensors;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.vinsce.ciotl.model.sensors.GenericData.GenericDataItem;

/**
 * Ambient (room) temperature Data in degree Celsius
 *
 * @since 1.0.0
 */
@Getter
public class AmbientTemperatureData extends Data {
    private final Float temperature;

    public AmbientTemperatureData(Float temperature) {
        this.temperature = temperature;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ temperature=" + temperature + " }";
    }

    @Override
    public GenericData toGenericData() {
        List<GenericDataItem> items = new ArrayList<>();
        items.add(new GenericDataItem("temperature", temperature));
        return new GenericData(items);
    }
}
