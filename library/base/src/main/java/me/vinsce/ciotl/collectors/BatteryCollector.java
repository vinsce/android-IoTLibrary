package me.vinsce.ciotl.collectors;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import me.vinsce.ciotl.model.samples.BatterySample;
import me.vinsce.ciotl.model.sensors.BatteryData;

/**
 * A Battery data collector.
 * Battery information are requested periodically using the {@link Intent#ACTION_BATTERY_CHANGED} intent filter
 *
 * @since 1.0.0
 */
public class BatteryCollector extends AbstractCollector<BatterySample, BatteryData> {

    private boolean initialized;
    private long samplingPeriod;

    private IntentFilter batteryIntentFilter;

    public BatteryCollector(@NonNull Context context) {
        this(context, 5000);
    }

    public BatteryCollector(@NonNull Context context, long samplingPeriod) {
        super(context);
        this.samplingPeriod = samplingPeriod;
    }

    @Override
    public void initialize() {
        batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.initialized = true;
    }


    @Override
    public boolean isSourceAvailable() {
        return true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void start() {
        if (!isInitialized())
            initialize();
        Log.i(LOG_TAG, "Starting battery collector");
        SHARED_HANDLER.post(this::getBatteryData);
    }

    private void getBatteryData() {
        Log.d(LOG_TAG, "Getting battery data");
        final Intent batteryStatus = context.registerReceiver(null, batteryIntentFilter);
        if (batteryStatus != null && batteryStatus.getExtras() != null) {

            Bundle batteryStatusExtras = batteryStatus.getExtras();
            String chargeStatus = batteryStatusExtras.getString("charge_status");
            Integer maxChargingVoltage = batteryStatusExtras.getInt("max_charging_voltage");
            Integer health = batteryStatusExtras.getInt("health");
            Integer maxChargingCurrent = batteryStatusExtras.getInt("max_charging_current");
            Integer status = batteryStatusExtras.getInt("status");
            Integer plugged = batteryStatusExtras.getInt("plugged");
            Integer seq = batteryStatusExtras.getInt("seq");
            Integer chargeCounter = batteryStatusExtras.getInt("charge_counter");
            Integer level = batteryStatusExtras.getInt("level");
            Integer scale = batteryStatusExtras.getInt("scale");
            Integer temperature = batteryStatusExtras.getInt("temperature");
            Integer voltage = batteryStatusExtras.getInt("voltage");
            Integer invalidCharger = batteryStatusExtras.getInt("invalid_charger");
            Boolean present = batteryStatusExtras.getBoolean("present");
            Boolean batteryLow = batteryStatusExtras.getBoolean("battery_low");

            BatteryData batteryData = new BatteryData(chargeStatus, maxChargingVoltage, health, maxChargingCurrent, status, plugged, present, seq, chargeCounter, level, scale, temperature, voltage, invalidCharger, batteryLow);
            BatterySample batterySample = new BatterySample(batteryData, deviceIdProvider.getDeviceId());

            notifyListeners(batterySample);
        } else {
            if (batteryStatus == null)
                Log.w(LOG_TAG, "Received null battery status intent");
            else
                Log.w(LOG_TAG, "Received null battery status intent extras");
        }
        SHARED_HANDLER.postDelayed(this::getBatteryData, samplingPeriod);
    }

    @Override
    public void stop() {
        SHARED_HANDLER.removeCallbacks(this::getBatteryData);
        Log.i(LOG_TAG, "Stopping Battery Collector");
    }

}