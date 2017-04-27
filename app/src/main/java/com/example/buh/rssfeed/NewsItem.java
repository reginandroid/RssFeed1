package com.example.buh.rssfeed;

/**
 * Created by Buh on 23.03.2017.
 */

public class NewsItem {
    private String newsHeading;
    private String newsDesc;
    private String author;
    private String dateTime;
    private String url;

    private String newsDescSmall;

    public NewsItem(String newsHeading, String newsDesc, String author, String dateTime, String url) {
        this.newsHeading = newsHeading;
        this.newsDesc = newsDesc;
        this.author = author;
        this.dateTime = dateTime;
        this.url = url;

        this.newsDescSmall = this.newsDesc.substring(0, 19) + "...";
    }


    public String getNewsDesc() {
        return newsDesc;
    }

    public String getAuthor() {
        return author;
    }

    public String getNewsHeading() {
        return newsHeading;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUrl() {
        return url;
    }


    public String getNewsDescSmall() {
        return newsDescSmall;
    }
}
