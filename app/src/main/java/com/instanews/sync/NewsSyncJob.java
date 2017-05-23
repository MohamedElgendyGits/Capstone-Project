package com.instanews.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.instanews.R;
import com.instanews.application.InstaNewsConstants;
import com.instanews.controller.NewsController;
import com.instanews.data.Contract;
import com.instanews.model.News;
import com.instanews.utils.ConnectionUtils;
import com.instanews.utils.JsonUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public final class NewsSyncJob {

    private static final String NEWS_JOB_TAG = "newsJobTag";
    private static final int periodicity = (int) TimeUnit.HOURS.toSeconds(6);
    private static final int toleranceInterval = (int) TimeUnit.HOURS.toSeconds(1);

    private NewsSyncJob() {
    }

    public static synchronized void initialize(final Context context) {
        schedulePeriodic(context);
        //syncImmediately(context);
    }

    private static void schedulePeriodic(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job job = createJob(dispatcher);
        dispatcher.schedule(job);
    }

    private static Job createJob(FirebaseJobDispatcher dispatcher) {

        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                // unique id of the task
                .setTag(NEWS_JOB_TAG)
                // We are mentioning that the job is periodic
                .setRecurring(true)
                // Run every 6 hours from now.
                .setTrigger(Trigger.executionWindow(periodicity, periodicity + toleranceInterval))
                // overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                //Run this job only when the network is available.
                .setConstraints(Constraint.ON_ANY_NETWORK)
                // Call this service when the criteria are met.
                .setService(NewsJobService.class)
                .build();

        return job;
    }

    public static void syncImmediately(Context context) {
        if (ConnectionUtils.isConnected(context)) {
            Intent nowIntent = new Intent(context, NewsIntentService.class);
            context.startService(nowIntent);
        } else {
            Toast.makeText(context, R.string.error_no_network, Toast.LENGTH_SHORT).show();
        }
    }

    public static void getNews(Context context) {
        Timber.d("Running news api request");

        ArrayList<News> news =  NewsController.submitRetrieveNewsRequest();
        saveNewsToDatabase(context, news);
    }

    private static void saveNewsToDatabase(Context context, ArrayList<News> news) {

        context.getContentResolver().delete(Contract.NewsContract.URI, null, null);

        ArrayList<ContentValues> sourcesCVs = new ArrayList<>();

        for (int i = 0; i < news.size(); i++) {

            ContentValues sourceCV = new ContentValues();
            sourceCV.put(Contract.NewsContract.COLUMN_NEWS_TITLE, news.get(i).getTitle());
            sourceCV.put(Contract.NewsContract.COLUMN_NEWS_DESCRIPTION, news.get(i).getDescription());
            sourceCV.put(Contract.NewsContract.COLUMN_NEWS_AUTHOR, news.get(i).getAuthor());
            sourceCV.put(Contract.NewsContract.COLUMN_PUBLISHED_DATE, news.get(i).getPublishedDate());
            String newsJsonString = JsonUtils.convertObjectToJsonString(news.get(i),News.class);
            sourceCV.put(Contract.NewsContract.COLUMN_NEWS_JSON, newsJsonString);

            sourcesCVs.add(sourceCV);
        }

        context.getContentResolver().bulkInsert(
                Contract.NewsContract.URI,
                sourcesCVs.toArray(new ContentValues[sourcesCVs.size()]));

        // update widget
        Intent dataUpdatedIntent = new Intent(InstaNewsConstants.ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

}
