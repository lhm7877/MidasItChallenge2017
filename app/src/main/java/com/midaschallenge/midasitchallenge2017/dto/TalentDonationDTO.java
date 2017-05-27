package com.midaschallenge.midasitchallenge2017.dto;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentDonationDTO {
    private int id;
    private String title;
    private String content;
    private long startDate;
    private long endDate;

    public TalentDonationDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
