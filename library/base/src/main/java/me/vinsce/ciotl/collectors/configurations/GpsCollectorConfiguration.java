package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationRequest;

import lombok.Getter;
import lombok.Setter;
import me.vinsce.ciotl.collectors.GpsCollector;

@Getter
@Setter
public class GpsCollectorConfiguration extends CollectorConfiguration {
    private LocationRequest locationRequest;

    public GpsCollectorConfiguration(long updateInterval, long fastestUpdateInterval) {
        this(new LocationRequest()
                .setInterval(updateInterval)
                .setFastestInterval(fastestUpdateInterval)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
    }

    public GpsCollectorConfiguration(LocationRequest locationRequest) {
        super("Gps");
        this.locationRequest = locationRequest;
    }

    @Override
    public GpsCollector createCollector(Context context) {
        return new GpsCollector(context, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "GpsCollectorConfiguration{" +
                "locationRequest=" + locationRequest +
                '}';
    }
}
