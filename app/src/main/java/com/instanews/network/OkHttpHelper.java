package com.instanews.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Mohamed Elgendy on 22/5/2017.
 */

public class OkHttpHelper {

    // declare instance from OkHttpClient
    private static OkHttpClient okHttpClient;

    private OkHttpHelper() {
        // private constructor
    }

    public static OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
