package me.vinsce.ciotl.model.sensors;

import androidx.annotation.NonNull;

import lombok.Getter;

/**
 * Gps Data contains: latitude, longitude, altitude, bearing and speed
 *
 * @since 1.0.0
 */
@Getter
public class GpsData extends Data {
    private final Double latitude;
    private final Double longitude;
    private final Double altitude;
    private final Float bearing;
    private final Float speed;

    public GpsData(Double latitude, Double longitude, Double altitude, float bearing, float speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.bearing = bearing;
        this.speed = speed;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ " +
                "latitude=" + latitude + "," +
                "longitude=" + longitude + "," +
                "altitude=" + altitude + "," +
                "bearing=" + bearing + "," +
                "speed=" + speed
                + " }";
    }
}