package me.vinsce.ciotl.model.sensors;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.vinsce.ciotl.model.sensors.GenericData.GenericDataItem;

/**
 * Battery Data contains all the information associated with the device battery (level, status, voltage, ...)
 *
 * @since 1.0.0
 */
@Getter
public class BatteryData extends Data {
    private final String chargeStatus;
    private final Integer maxChargingVoltage;
    private final Integer health;
    private final Integer maxChargingCurrent;
    private final Integer status;
    private final Integer plugged;
    private final Boolean present;
    private final Integer seq;
    private final Integer chargeCounter;
    private final Integer level;
    private final Integer scale;
    private final Integer temperature;
    private final Integer voltage;
    private final Integer invalidCharger;
    private final Boolean batteryLow;

    public BatteryData(String chargeStatus, Integer maxChargingVoltage, Integer health, Integer maxChargingCurrent, Integer status, Integer plugged, Boolean present, Integer seq, Integer chargeCounter, Integer level, Integer scale, Integer temperature, Integer voltage, Integer invalidCharger, Boolean batteryLow) {
        this.chargeStatus = chargeStatus;
        this.maxChargingVoltage = maxChargingVoltage;
        this.health = health;
        this.maxChargingCurrent = maxChargingCurrent;
        this.status = status;
        this.plugged = plugged;
        this.present = present;
        this.seq = seq;
        this.chargeCounter = chargeCounter;
        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.invalidCharger = invalidCharger;
        this.batteryLow = batteryLow;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "chargeStatus='" + chargeStatus + '\'' +
                ", maxChargingVoltage=" + maxChargingVoltage +
                ", health=" + health +
                ", maxChargingCurrent=" + maxChargingCurrent +
                ", status=" + status +
                ", plugged=" + plugged +
                ", present=" + present +
                ", seq=" + seq +
                ", chargeCounter=" + chargeCounter +
                ", level=" + level +
                ", scale=" + scale +
                ", temperature=" + temperature +
                ", voltage=" + voltage +
                ", invalidCharger=" + invalidCharger +
                ", batteryLow=" + batteryLow +
                '}';
    }

    @Override
    public GenericData toGenericData() {
        List<GenericDataItem> items = new ArrayList<>();

        items.add(new GenericDataItem("chargeStatus", "chargeStatus"));
        items.add(new GenericDataItem("maxChargingVoltage", "maxChargingVoltage"));
        items.add(new GenericDataItem("health", "health"));
        items.add(new GenericDataItem("maxChargingCurrent", "maxChargingCurrent"));
        items.add(new GenericDataItem("status", "status"));
        items.add(new GenericDataItem("plugged", "plugged"));
        items.add(new GenericDataItem("present", "present"));
        items.add(new GenericDataItem("seq", "seq"));
        items.add(new GenericDataItem("chargeCounter", "chargeCounter"));
        items.add(new GenericDataItem("level", "level"));
        items.add(new GenericDataItem("scale", "scale"));
        items.add(new GenericDataItem("temperature", "temperature"));
        items.add(new GenericDataItem("voltage", "voltage"));
        items.add(new GenericDataItem("invalidCharger", "invalidCharger"));
        items.add(new GenericDataItem("batteryLow", "batteryLow"));

        return new GenericData(items);
    }
}
