package com.midaschallenge.midasitchallenge2017.dto;

import java.io.Serializable;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentDonationDTO implements Serializable {
    private int id;
    private int user_id;
    private String title;
    private String contents;
    private boolean completed;
    private long req_at;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getReq_at() {
        return req_at;
    }

    public void setReq_at(long req_at) {
        this.req_at = req_at;
    }

    private long start_at;
    private long end_at;

    public TalentDonationDTO(String title, String content) {
        this.title = title;
        this.contents = content;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getStart_at() {
        return start_at;
    }

    public void setStart_at(long start_at) {
        this.start_at = start_at;
    }

    public long getEnd_at() {
        return end_at;
    }

    public void setEnd_at(long end_at) {
        this.end_at = end_at;
    }

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
}
