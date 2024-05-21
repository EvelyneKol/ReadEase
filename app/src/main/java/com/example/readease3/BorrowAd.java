package com.example.readease3;

public class BorrowAd {
    private int borrowAdId;
    private String borrowAdIsbn;
    private int borrowPublisher;
    private String borrowStatus;
    private String borrowLocation; // Add this field

    // Constructor
    public BorrowAd(int borrowAdId, String borrowAdIsbn, int borrowPublisher, String borrowStatus, String borrowLocation) {
        this.borrowAdId = borrowAdId;
        this.borrowAdIsbn = borrowAdIsbn;
        this.borrowPublisher = borrowPublisher;
        this.borrowStatus = borrowStatus;
        this.borrowLocation = borrowLocation; // Initialize this field
    }

    // Getters
    public int getBorrowAdId() {
        return borrowAdId;
    }

    public String getBorrowAdIsbn() {
        return borrowAdIsbn;
    }

    public int getBorrowPublisher() {
        return borrowPublisher;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public String getBorrowLocation() {
        return borrowLocation; // Add this getter
    }
}
