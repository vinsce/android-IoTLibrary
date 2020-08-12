package me.vinsce.ciotl.model.sensors;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.vinsce.ciotl.model.sensors.GenericData.GenericDataItem;

/**
 * Data collected from light sensor.
 *
 * @since 1..0.0
 */
@Getter
public class LightData extends Data {
    private final Float light;

    public LightData(Float light) {
        this.light = light;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ light=" + light + " }";
    }

    @Override
    public GenericData toGenericData() {
        List<GenericDataItem> items = new ArrayList<>();
        items.add(new GenericDataItem("light", light));
        return new GenericData(items);
    }
}
