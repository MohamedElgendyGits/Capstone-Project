package com.instanews.application;

import android.app.Application;

import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * Created by Mohamed Elgendy on 22/5/2017.
 */

public class InstaNewsApplication extends Application {

    private static InstaNewsApplication instance;

    public static InstaNewsApplication getInstance() {
        if (instance == null){
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
