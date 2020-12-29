package com.example.school.data;

public class Card {
    private String name;//姓名
    private String time;//时间
    private String content;//内容
    private int avaId;//用户头像
    private int id;//评论id
    private Boolean like;
    private String location;
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvaId() {
        return avaId;
    }

    public void setAvaId(int avaId) {
        this.avaId = avaId;
    }
}
