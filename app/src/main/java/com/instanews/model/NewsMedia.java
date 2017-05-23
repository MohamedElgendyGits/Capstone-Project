package com.instanews.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsMedia {

    @SerializedName("url")
    private String thumbnailUrl;
    private String format;
    private String height;
    private String width;
    private String type;
    private String subtype;
    private String caption;
    private String copyright;


    /* -- all formats --
    Standard Thumbnail  >  "height": 75,   "width": 75
    thumbLarge          >  "height": 150,  "width": 150
    Normal              >  "height": 129,  "width": 190
    mediumThreeByTwo210 >  "height": 140,  "width": 210
    superJumbo          >  "height": 1396, "width": 2048,
    */


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
