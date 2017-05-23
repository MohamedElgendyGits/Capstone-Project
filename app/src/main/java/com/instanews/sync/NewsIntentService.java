package com.instanews.sync;

import android.app.IntentService;
import android.content.Intent;

import timber.log.Timber;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsIntentService extends IntentService {

    public NewsIntentService() {
        super(NewsIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("IntentService handled");
        NewsSyncJob.getNews(getApplicationContext());
    }
}
