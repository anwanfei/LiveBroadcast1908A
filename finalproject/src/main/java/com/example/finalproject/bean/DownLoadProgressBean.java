package com.example.finalproject.bean;

public class DownLoadProgressBean {
    private long count;
    private long contentLength;

    public DownLoadProgressBean(long count, long contentLength) {
        this.count = count;
        this.contentLength = contentLength;
    }

    public long getCount() {
        return count;
    }

    public long getContentLength() {
        return contentLength;
    }
}
