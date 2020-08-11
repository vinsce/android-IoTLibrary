package me.vinsce.ciotl.model.sensors;

import java.util.List;

/**
 * Base class for measured data
 *
 * @since 1.0.0
 */
public abstract class Data {
    public abstract GenericData toGenericData();
}