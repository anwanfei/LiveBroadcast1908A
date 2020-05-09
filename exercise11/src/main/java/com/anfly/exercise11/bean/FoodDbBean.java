package com.anfly.exercise11.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FoodDbBean {
    @Id(autoincrement = true)
    private long id;
    private String name;
    private String url;
    @Generated(hash = 2001692676)
    public FoodDbBean(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    @Generated(hash = 649472808)
    public FoodDbBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
