package com.example.finalproject.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FoodDbBean {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String path;
    @Generated(hash = 1547891973)
    public FoodDbBean(Long id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }
    @Generated(hash = 649472808)
    public FoodDbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
