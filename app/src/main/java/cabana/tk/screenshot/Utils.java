package cabana.tk.screenshot;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.Set;

/**
 * Created by k on 2016-5-2.
 */
public class Utils {
    public static Context getContext() {
        return ShotApplication.getContext();
    }

    public static void createPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void SPSaveValu(String key, String value) {
        SharedPreferences.Editor edit = ShotApplication.getSP().edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void SPSaveValu(String key, int value) {
        SharedPreferences.Editor edit = ShotApplication.getSP().edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void SPSaveValu(String key, float value) {
        SharedPreferences.Editor edit = ShotApplication.getSP().edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public static void SPSaveValu(String key, Boolean value) {
        SharedPreferences.Editor edit = ShotApplication.getSP().edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void SPSaveValu(String key, Set<String> values) {
        SharedPreferences.Editor edit = ShotApplication.getSP().edit();
        edit.putStringSet(key, values);
        edit.commit();
    }
}
