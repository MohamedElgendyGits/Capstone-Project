package com.instanews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsData {

    private String status;
    private String copyright;
    private String section;
    private String last_updated;
    private String num_results;
    @SerializedName("results")
    private ArrayList<News> news;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getNum_results() {
        return num_results;
    }

    public void setNum_results(String num_results) {
        this.num_results = num_results;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }
}
