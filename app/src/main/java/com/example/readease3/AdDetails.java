package com.example.readease3;
public class AdDetails {
    private String description;

    private String title;

    private int pages;
    private float price;
    private String publisherName;

    public AdDetails(String title, String description, int pages, float price, String publisherName) {
        this.title = title;
        this.description = description;
        this.pages = pages;
        this.price = price;
        this.publisherName = publisherName;
    }

    // Getters for each field
    public String getTitle() {return title;}
    public String getDescription() {
        return description;
    }

    public int getPages() {
        return pages;
    }

    public float getPrice() {
        return price;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
