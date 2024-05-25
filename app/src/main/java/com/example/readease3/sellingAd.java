package com.example.readease3;

public class sellingAd extends Book {
    private int sellingAdId;
    private float sellingPrice;
    private int sellingPublisher;
    private String sellingStatus;
    private String publisherName;

    // Constructor
    public sellingAd(String isbn, String title, String author, String description, int pages, String category,
                     int sellingAdId, float sellingPrice, int sellingPublisher, String sellingStatus, String publisherName) {
        super(isbn, title, author, description, pages, category);
        this.sellingAdId = sellingAdId;
        this.sellingPrice = sellingPrice;
        this.sellingPublisher = sellingPublisher;
        this.sellingStatus = sellingStatus;
        this.publisherName = publisherName;
    }

    // Getters and setters
    public int getSellingAdId() {
        return sellingAdId;
    }

    public void setSellingAdId(int sellingAdId) {
        this.sellingAdId = sellingAdId;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getSellingPublisher() {
        return sellingPublisher;
    }

    public void setSellingPublisher(int sellingPublisher) {
        this.sellingPublisher = sellingPublisher;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
