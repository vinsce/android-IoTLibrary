package me.vinsce.ciotl.model.sensors;

import java.util.List;

import lombok.Getter;

@Getter
public class GenericData extends Data {
    private final List<GenericDataItem> values;

    public GenericData(List<GenericDataItem> values) {
        this.values = values;
    }

    @Override
    public GenericData toGenericData() {
        return this;
    }

    public static class GenericDataItem {
        private String name;
        private Object value;

        public GenericDataItem(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
