package com.alan.asm.vnexpressdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Channel {

    private String title;
    private String imageUrl;
    private String pubDate;
    private List<RssItem> rssItems;

    public Channel() {
        rssItems = new ArrayList<>();
    }

    public Channel(String title, String imageUrl, String pubDate, List<RssItem> rssItems) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.pubDate = pubDate;
        this.rssItems = rssItems;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setRssItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public List<RssItem> getRssItems() {
        return rssItems;
    }

    public void addArticle(RssItem rssItem) {
        if (rssItem == null) {
            return;
        }
        if (rssItems == null) {
            rssItems = new ArrayList<>();
        }
        rssItems.add(rssItem);
    }
}