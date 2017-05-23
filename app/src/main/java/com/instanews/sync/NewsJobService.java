package com.instanews.sync;

import android.content.Intent;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsJobService extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {

        // calling intent service to do our background call
        Intent nowIntent = new Intent(getApplicationContext(), NewsIntentService.class);
        getApplicationContext().startService(nowIntent);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}