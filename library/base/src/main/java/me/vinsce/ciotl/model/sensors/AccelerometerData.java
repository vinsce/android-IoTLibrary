package me.vinsce.ciotl.model.sensors;


import androidx.annotation.NonNull;

import lombok.Getter;

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
}
