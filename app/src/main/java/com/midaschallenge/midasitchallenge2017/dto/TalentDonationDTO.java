package com.midaschallenge.midasitchallenge2017.dto;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentDonationDTO {
    private int id;
    private String title;
    private String contents;
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
