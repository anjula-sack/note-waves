package com.example.notewave.models;

public class Note {
    private long id;
    private String title;
    private String date;
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Note(String title, String date, String body) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.body = body;
    }
}
