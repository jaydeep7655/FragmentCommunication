package com.example.t186.fragmentcommunication;

import android.app.Application;
import android.content.Context;

import com.example.t186.fragmentcommunication.RoomDataBase.AppDatabase;

/**
 * Created by T186 on 4/28/2018.
 */

public class MyApplication extends Application {
    public Context applicationContext;
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        appDatabase = AppDatabase.getDatabase(applicationContext);
    }
}
