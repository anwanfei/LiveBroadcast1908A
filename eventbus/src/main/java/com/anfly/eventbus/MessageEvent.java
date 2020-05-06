package com.anfly.eventbus;

public class MessageEvent {
    private String name;
    private String age;
    private String msg;

    public MessageEvent(String name, String age, String msg) {
        this.name = name;
        this.age = age;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
