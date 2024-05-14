package com.example.readease3;

public class Quiz {
    private int id;
    private String title;

    public Quiz(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
