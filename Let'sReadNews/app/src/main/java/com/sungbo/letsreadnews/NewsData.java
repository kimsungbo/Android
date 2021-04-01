package com.sungbo.letsreadnews;

import java.io.Serializable;

public class NewsData implements Serializable {
    private String title;
    private String urlToImage;
    private String content;

    public String getInternetUrl() {
        return internetUrl;
    }

    public void setInternetUrl(String internetUrl) {
        this.internetUrl = internetUrl;
    }

    private String internetUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
