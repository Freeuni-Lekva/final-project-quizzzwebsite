package com.example.quizzzwebsite;

import java.sql.Date;

public class Announcement {
    private String title;
    private String creatorName;
    private String text;
    private Date date;
    public Announcement(String title, String creatorName, String text, Date date) {
        this.title = title;
        this.creatorName = creatorName;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getText(){
        return text;
    }

    public Date getDate(){
        return date;
    }
}
