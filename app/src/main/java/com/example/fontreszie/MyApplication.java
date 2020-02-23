package com.example.fontreszie;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import static com.example.fontreszie.SettingsStorageHelper.FONT_SIZE;

public class MyApplication extends Application {
    private static MyApplication sInstance;


    public static MyApplication getInstance() {
        return sInstance;
    }



    @Override
    public void onCreate() {
        Log.i("MyApplication", "onCreate" );
        super.onCreate();
        sInstance =this;
    }



    public static Float getFontSize(){

        float fontSize = Float.parseFloat(SettingsStorageHelper.get(FONT_SIZE, Float.toString(15)));
        Log.i("MyApplication", "fontSize" + fontSize);
        return fontSize;
    }


}
