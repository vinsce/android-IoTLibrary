package me.vinsce.ciotl.model.samples;

import java.sql.Timestamp;

import me.vinsce.ciotl.model.sensors.Data;
import me.vinsce.ciotl.model.sensors.GenericData;

public class GenericSample extends Sample<GenericData> {
    public GenericSample(Timestamp timestamp, GenericData data, String device, String type) {
        super(timestamp, data, device, type);
    }

    public GenericSample(Sample<? extends Data> sample) {
        this(sample.getTimestamp(), sample.getData().toGenericData(), sample.getDevice(), sample.getType());
    }
}