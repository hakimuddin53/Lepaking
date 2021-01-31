package com.example.airbillscanner.common.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

/**
 * Created by jhcheng on 9/26/2017.
 */

public class PreferenceHelper {

    private static String TAG = PreferenceHelper.class.getSimpleName();

    public static SharedPreferences getSharedPreferences(Context context, String preferenceName){
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public static Boolean isPrefContains(int resourceID, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.contains(context.getString(resourceID));
    }

    public static void removePref(int resourceID, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().remove(context.getString(resourceID)).apply();
    }

    public static String getString(int resourceID, String defaultValue, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.getString(context.getString(resourceID), defaultValue);
    }

    public static void setString(int resourceID, String value, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().putString(context.getString(resourceID), value).apply();
    }

    public static Boolean getBoolean(int resourceID, Boolean defaultValue, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.getBoolean(context.getString(resourceID), defaultValue);
    }

    public static void setBoolean(int resourceID, Boolean value, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().putBoolean(context.getString(resourceID), value).apply();
    }

    public static Integer getInt(int resourceID, Integer defaultValue, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.getInt(context.getString(resourceID), defaultValue);
    }

    public static void setInt(int resourceID, Integer value, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().putInt(context.getString(resourceID), value).apply();
    }

    public static Long getLong(int resourceID, Long defaultValue, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.getLong(context.getString(resourceID), defaultValue);
    }

    public static void setLong(int resourceID, Long value, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().putLong(context.getString(resourceID), value).apply();
    }

    public static Float getFloat(int resourceID, Float defaultValue, Context context, SharedPreferences sharedPreferences){
        return sharedPreferences.getFloat(context.getString(resourceID), defaultValue);
    }

    public static void setFloat(int resourceID, Float value, Context context, SharedPreferences sharedPreferences){
        sharedPreferences.edit().putFloat(context.getString(resourceID), value).apply();
    }

    /***
     * Runs on a map of values (key-value) and inserts into the shared preferences
     * This can be used also on values which are maps themselves
     * Limitation:
     * <ul>
     *     <li> Can't parse a value which is a list
     *     <li> Float will be case to Double
     * </ul>
     * @param map The Map containing values to apply
     */
    public static void setMap(Map<String, Object> map, SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        runOverMap(map, editor);
        editor.apply();
    }

    private static void runOverMap(Map<String, Object> map, SharedPreferences.Editor edit) {
        for (String key : map.keySet()) {
            Object value = map.get(key);

            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                edit.putFloat(key, (Float) value);
            } else if (value instanceof Double) {
                Log.w(TAG, "Warning: Converting a Double into a Float");
                edit.putFloat(key, (float) ((double) ((Double) value)));
            } else if (value instanceof Long) {
                edit.putLong(key, (Long) value);
            } else if (value instanceof Map) {
                // Recursively call next map
                runOverMap((Map) value, edit);
            } else {
                Log.e(TAG, "Trying to enter unknown type inside a shared pref map. type = " + value.getClass().getSimpleName());
            }
        }
    }
}
