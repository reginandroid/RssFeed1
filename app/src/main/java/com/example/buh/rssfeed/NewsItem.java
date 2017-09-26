package com.example.buh.rssfeed;

    public class NewsItem {
    private String newsHeading;
    private String newsDesc;
    private String author;
    private String dateTime;
    private String url;
    private String imageurl;

    public void setNewsHeading(String newsHeading) {
        this.newsHeading = newsHeading;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setNewsDescSmall(String newsDescSmall) {
        this.newsDescSmall = newsDescSmall;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    private String newsDescSmall;

    public NewsItem() {
    }

    public NewsItem(String newsHeading, String newsDesc, String author, String dateTime, String url, String imageurl) {
        this.newsHeading = newsHeading;
        this.newsDesc = newsDesc;
        this.author = author;
        this.dateTime = dateTime;
        this.url = url;
        this.imageurl = imageurl;

        this.newsDescSmall = this.newsDesc.substring(0, 59) + "...";
    }


    public String getNewsDesc() {
        return newsDesc.substring(0, 59) + "...";
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
