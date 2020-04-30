package com.anfly.collectionmvp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ConllectionDbBean {
    @Id(autoincrement = true)
    private long id;
    private String title;
    private String urlPath;
    @Generated(hash = 1530938490)
    public ConllectionDbBean(long id, String title, String urlPath) {
        this.id = id;
        this.title = title;
        this.urlPath = urlPath;
    }
    @Generated(hash = 897406299)
    public ConllectionDbBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrlPath() {
        return this.urlPath;
    }
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
