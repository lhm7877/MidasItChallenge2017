package com.midaschallenge.midasitchallenge2017.dto;

/**
 * Created by bo on 2017. 5. 28..
 */

public class DonationItem {
    private int id;
    private String title;
    private String contents;
    private long due_date;
    private int target_point;
    private int owned_point;
    private int contri_point;

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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getDue_date() {
        return due_date;
    }

    public void setDue_date(long due_date) {
        this.due_date = due_date;
    }

    public int getTarget_point() {
        return target_point;
    }

    public void setTarget_point(int target_point) {
        this.target_point = target_point;
    }

    public int getOwned_point() {
        return owned_point;
    }

    public void setOwned_point(int owned_point) {
        this.owned_point = owned_point;
    }

    public int getContri_point() {
        return contri_point;
    }

    public void setContri_point(int contri_point) {
        this.contri_point = contri_point;
    }
}
