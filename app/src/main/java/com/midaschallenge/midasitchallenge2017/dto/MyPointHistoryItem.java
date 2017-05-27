package com.midaschallenge.midasitchallenge2017.dto;

/**
 * Created by Hooo on 2017-05-28.
 */

public class MyPointHistoryItem {
    private int id;
    private int user_id;
    private Long date;
    private int point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
