package com.alan.asm.vnexpressdemo.model;

import com.alan.asm.vnexpressdemo.utils.DateTimeUtil;

import java.util.Date;

public class RssItem {

    private String title;
    private String description;
    private String link;
    private Date pubDate;
    private String imageUrl;
    private ImageType imageType;

    public RssItem() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDateText, String format) {
        this.pubDate = DateTimeUtil.toDate(pubDateText, format);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public void setImageType(ImageType type) {
        this.imageType = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate(String format) {
        if (pubDate == null) return "";
        return DateTimeUtil.formatDate(pubDate, format);
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ImageType getImageType() {
        return imageType;
    }

}
