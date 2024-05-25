package com.example.readease3;

public class Purchase extends sellingAd {
    private int id;
    private int buyerId;
    private String typePurchase;

    // Constructor
    public Purchase(int id, int buyerId, String typePurchase, String isbn, String title, String author,
                    String description, int pages, String category, int sellingAdId, float sellingPrice,
                    int sellingPublisher, String sellingStatus, String publisherName) {
        super(isbn, title, author, description, pages, category, sellingAdId, sellingPrice, sellingPublisher, sellingStatus, publisherName);
        this.id = id;
        this.buyerId = buyerId;
        this.typePurchase = typePurchase;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getTypePurchase() {
        return typePurchase;
    }

    public void setTypePurchase(String typePurchase) {
        this.typePurchase = typePurchase;
    }
}
