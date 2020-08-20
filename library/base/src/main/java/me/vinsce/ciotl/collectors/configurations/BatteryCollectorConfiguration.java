package me.vinsce.ciotl.collectors.configurations;

import android.content.Context;

import androidx.annotation.NonNull;

import lombok.Getter;
import me.vinsce.ciotl.collectors.BatteryCollector;

@Getter
public class BatteryCollectorConfiguration extends CollectorConfiguration {

    private long samplingPeriod;

    /**
     * @param samplingPeriod sampling period in ms
     */
    public BatteryCollectorConfiguration(long samplingPeriod) {
        super("Battery");
        this.samplingPeriod = samplingPeriod;
    }


    @Override
    public BatteryCollector createCollector(Context context) {
        return new BatteryCollector(context, this);
    }

    @NonNull
    @Override
    public String toString() {
        return "BatteryCollectorConfiguration{" +
                "samplingPeriod=" + samplingPeriod +
                '}';
    }
}