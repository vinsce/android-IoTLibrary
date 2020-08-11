package me.vinsce.ciotl.helpers.id;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class GeneratedUUIDProvider implements DeviceIdProvider {
    private static final String ID_PREF_KEY = "iot_device_id";
    private String id;

    public GeneratedUUIDProvider(Context context) {
        initialize(context);
    }

    private void initialize(Context context) {
        if (id == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(ID_PREF_KEY, Context.MODE_PRIVATE);
            id = sharedPrefs.getString(ID_PREF_KEY, null);
            if (id == null) {
                id = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(ID_PREF_KEY, id);
                editor.apply();
            }
        }
    }

    @Override
    public String getDeviceId() {
        return id;
    }
}
