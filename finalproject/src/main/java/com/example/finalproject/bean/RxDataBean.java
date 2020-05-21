package com.example.finalproject.bean;

public class RxDataBean {
    /**
     * bannerUrl : https://www.wanandroid.com/banner/json
     * articleList : https://www.wanandroid.com/project/list/1/json?cid=294
     */

    private String bannerUrl;
    private String articleList;

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getArticleList() {
        return articleList;
    }

    public void setArticleList(String articleList) {
        this.articleList = articleList;
    }
}
