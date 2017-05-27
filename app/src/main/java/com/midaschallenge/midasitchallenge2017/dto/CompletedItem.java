package com.midaschallenge.midasitchallenge2017.dto;

/**
 * Created by bo on 2017. 5. 28..
 */

public class CompletedItem {
    private int id;
    private int talent_id;
    private String title;
    private String content;
    private int contributor_id;
    private long completed_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTalent_id() {
        return talent_id;
    }

    public void setTalent_id(int talent_id) {
        this.talent_id = talent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getContributor_id() {
        return contributor_id;
    }

    public void setContributor_id(int contributor_id) {
        this.contributor_id = contributor_id;
    }

    public long getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(long completed_at) {
        this.completed_at = completed_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
