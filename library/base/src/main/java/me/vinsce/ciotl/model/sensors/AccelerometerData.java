package me.vinsce.ciotl.model.sensors;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.vinsce.ciotl.model.sensors.GenericData.GenericDataItem;

/**
 * Accelerometer Data contains the acceleration in the three directions (x,y,z)
 *
 * @since 1.0.0
 */
@Getter
public class AccelerometerData extends Data {
    private final Float accelerationX;
    private final Float accelerationY;
    private final Float accelerationZ;

    public AccelerometerData(Float accelerationX, Float accelerationY, Float accelerationZ) {
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.accelerationZ = accelerationZ;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ X=" + accelerationX + ", Y=" + accelerationY + ", Z=" + accelerationZ + " }";
    }

    @Override
    public GenericData toGenericData() {
        List<GenericDataItem> items = new ArrayList<>();
        items.add(new GenericDataItem("accelerationX", accelerationX));
        items.add(new GenericDataItem("accelerationY", accelerationY));
        items.add(new GenericDataItem("accelerationZ", accelerationZ));
        return new GenericData(items);
    }
}
