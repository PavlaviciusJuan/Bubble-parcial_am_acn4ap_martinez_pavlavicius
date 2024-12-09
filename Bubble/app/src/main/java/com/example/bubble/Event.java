package com.example.bubble;

public class Event {
    private String name;
    private String date;
    private String imageUrl;

    public Event() {
    }

    public Event(String name, String date, String imageUrl) {
        this.name = name;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

