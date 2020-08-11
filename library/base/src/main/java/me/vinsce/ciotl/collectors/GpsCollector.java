package me.vinsce.ciotl.collectors;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import me.vinsce.ciotl.model.samples.GpsSample;
import me.vinsce.ciotl.model.sensors.GpsData;

/**
 * A GPS collector that uses Google Play Services {@link FusedLocationProviderClient} to obtain gps values
 *
 * @since 1.0.0
 */
public class GpsCollector extends AbstractCollector<GpsSample, GpsData> {

    private FusedLocationProviderClient locationProvider;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private final long updateInterval;
    private final long fastestUpdateInterval;

    private boolean initialized;

    /**
     * Create a new GpsCollector using the default values for updatedInterval ({@code 1000 ms})
     * and fastestUpdatedInterval ({@code 500 ms})
     *
     * @param context Context used to access location service
     */
    public GpsCollector(@NonNull Context context) {
        this(context, 1000, 500);
    }

    /**
     * Create a new GpsCollector with the specified update intervals
     *
     * @param context               Context used to access location service
     * @param updateInterval        in ms, the location update interval
     * @param fastestUpdateInterval in ms, the fastest location update interval
     */
    public GpsCollector(@NonNull Context context, long updateInterval, long fastestUpdateInterval) {
        super(context);
        this.updateInterval = updateInterval;
        this.fastestUpdateInterval = fastestUpdateInterval;
    }

    /**
     * Create a new GpsCollector with the specified {@link LocationRequest}
     */
    public GpsCollector(@NonNull Context context, @NonNull LocationRequest locationRequest) {
        super(context);
        this.locationRequest = locationRequest;
        this.updateInterval = locationRequest.getInterval();
        this.fastestUpdateInterval = locationRequest.getFastestInterval();
    }

    @Override
    public void initialize() {
        locationProvider = LocationServices.getFusedLocationProviderClient(context);

        if (locationRequest == null)
            locationRequest = new LocationRequest()
                    .setInterval(updateInterval)
                    .setFastestInterval(fastestUpdateInterval)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (this.locationCallback == null)
            this.locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    final Location location = locationResult.getLastLocation();

                    double latitude = location.getLatitude();
                    double longitude = location.getLatitude();
                    double altitude = location.getAltitude();
                    float bearing = location.getBearing();
                    float speed = location.getSpeed();

                    final GpsData data = new GpsData(latitude, longitude, altitude, bearing, speed);
                    final GpsSample sample = new GpsSample(data, deviceIdProvider.getDeviceId());

                    notifyListeners(sample);
                }
            };
        this.initialized = true;
    }


    @Override
    public boolean isSourceAvailable() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void start() {
        if (!isSourceAvailable())
            throw new RuntimeException("Gps sensor not available");
        if (!isInitialized())
            initialize();
        if (!isInitialized())
            throw new RuntimeException("Unable to initialize collector");

        Log.i(LOG_TAG, "Requesting location updates");
        try {
            locationProvider.requestLocationUpdates(locationRequest, locationCallback, SHARED_HANDLER.getLooper());
        } catch (SecurityException unlikely) {
            Log.e(LOG_TAG, "Unable to request updates. " + unlikely);
        }
    }

    @Override
    public void stop() {
        Log.i(LOG_TAG, "Removing location updates");
        try {
            locationProvider.removeLocationUpdates(locationCallback);
        } catch (SecurityException unlikely) {
            Log.e(LOG_TAG, "Unable to remove location updates. " + unlikely);
        }
    }

}