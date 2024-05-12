package com.example.readease3;

public class sellingAd {
    private int sellingAdId;
    private String sellingAdIsbn;
    private float sellingPrice;
    private int sellingPublisher;
    private String sellingStatus;

    // Constructor
    public sellingAd(int sellingAdId, String sellingAdIsbn, float sellingPrice, int sellingPublisher, String sellingStatus) {
        this.sellingAdId = sellingAdId;
        this.sellingAdIsbn = sellingAdIsbn;
        this.sellingPrice = sellingPrice;
        this.sellingPublisher = sellingPublisher;
        this.sellingStatus = sellingStatus;
    }

    // Getters
    public int getSellingAdId() {
        return sellingAdId;
    }

    public String getSellingAdIsbn() {
        return sellingAdIsbn;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public int getSellingPublisher() {
        return sellingPublisher;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }
}
