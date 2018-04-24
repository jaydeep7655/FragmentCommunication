package com.example.t186.fragmentcommunication.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class ApplicationPreferences {


    /* For Set and Get String Value in Preference */
    public static void setValue(String key, String value, Context context) {

        getSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    @Nullable
    public static String getValue(String key, Context context) {
        return getValue(key, null, context);
    }

    public static String getValue(String key, @Nullable String defaultValue,
                                  Context context) {
        return getSharedPreferences(context)
                .getString(key, defaultValue);
    }

    public static void setIntValue(String key, int value, Context context) {
        getSharedPreferences(context)
                .edit()
                .putInt(key, value)
                .apply();
    }

    public static int getIntValue(String key, Context context) {
        return getSharedPreferences(context)
                .getInt(key, 0);
    }

    public static void setBooleanValue(String key, boolean value,
                                       Context context) {
        getSharedPreferences(context)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static boolean getBooleanValue(String key, Context context) {
        return getBooleanValue(key, false, context);
    }

    public static boolean getBooleanValue(String key, boolean defaultValue, Context context) {
        return getSharedPreferences(context)
                .getBoolean(key, defaultValue);
    }


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

  /*  public static void setDownloadableEntity(String key, List<DownloadTask> downloadableAudioEntity
            , Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(downloadableAudioEntity);
        getSharedPreferences(context).
                edit().
                putString(key, json)
                .apply();


    }

    public static List<DownloadTask> getDownloadEntitye(String key, Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(key, null);
        DownloadTask downloadableAudioEntity = gson.fromJson(json, DownloadTask.class);
        return (List<DownloadTask>) downloadableAudioEntity;
    }*/
}
