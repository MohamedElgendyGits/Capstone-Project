package com.instanews.controller;

import com.instanews.application.InstaNewsApplication;
import com.instanews.data.SettingsSharedPref;
import com.instanews.model.News;
import com.instanews.model.NewsData;
import com.instanews.network.OkHttpHelper;
import com.instanews.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsController {

    private static final String API_KEY = "d1d9fc9a7fc348ebb9cc408dd430fcb3";

    private static String sourceUrlBuilder() {

        String desiredCategory = SettingsSharedPref.getDesiredCategory(InstaNewsApplication.getInstance());
        String requestedUrl = "https://api.nytimes.com/svc/topstories/v2/"+desiredCategory+".json?api-key="+API_KEY;

        return requestedUrl;
    }

    public static ArrayList<News> submitRetrieveNewsRequest() {

        Request request = new Request.Builder()
                .url(sourceUrlBuilder())
                .build();

        String responseString = null;
        try {
            Response response = OkHttpHelper.getClient().newCall(request).execute();
            responseString = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        NewsData newsData = JsonUtils.convertJsonStringToObject(responseString, NewsData.class);
        return newsData.getNews();
    }

}
