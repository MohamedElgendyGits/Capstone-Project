package com.instanews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class News {

    private String section;
    private String subsection;
    private String item_type;
    private String title;
    @SerializedName("abstract")
    private String description;
    @SerializedName("url")
    private String newsUrl;
    @SerializedName("byline")
    private String author;
    @SerializedName("published_date")
    private String publishedDate;
    private String updated_date;
    private ArrayList<NewsMedia> multimedia;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public ArrayList<NewsMedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<NewsMedia> multimedia) {
        this.multimedia = multimedia;
    }
}
