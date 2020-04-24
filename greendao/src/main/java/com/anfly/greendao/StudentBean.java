package com.anfly.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StudentBean {

    @Id(autoincrement = true)
    private long id;
    @NotNull
    private String name;
    private String age;
    @Generated(hash = 1498510806)
    public StudentBean(long id, @NotNull String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 2097171990)
    public StudentBean() {
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
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
