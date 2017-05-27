package com.midaschallenge.midasitchallenge2017.dto;

import java.io.Serializable;

/**
 * Created by bo on 2017. 5. 27..
 */

public class CurrentServiceItem implements Serializable {
    private int id;
    private String title;
    private String due_date;
    private String target_point;
    private String owned_point;
    private String contents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTarget_point() {
        return target_point;
    }

    public void setTarget_point(String target_point) {
        this.target_point = target_point;
    }

    public String getOwned_point() {
        return owned_point;
    }

    public void setOwned_point(String owned_point) {
        this.owned_point = owned_point;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
