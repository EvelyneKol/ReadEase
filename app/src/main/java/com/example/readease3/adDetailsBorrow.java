package com.example.readease3;

public class adDetailsBorrow{
    private int borrowAdId;
    private String borrowAdIsbn;
    private String borrowPublisher;
    private String borrowStatus;
    private String borrowLocation; // Add this field
    private String bookTitle;
    private String bookDescription;
    private int bookPages;

    // Constructor
    public adDetailsBorrow(int borrowAdId, String borrowAdIsbn, String borrowPublisher, String borrowStatus, String borrowLocation, String bookTitle, String bookDescription, int bookPages) {
        this.borrowAdId = borrowAdId;
        this.borrowAdIsbn = borrowAdIsbn;
        this.borrowPublisher = borrowPublisher;
        this.borrowStatus = borrowStatus;
        this.borrowLocation = borrowLocation; // Initialize this field
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookPages = bookPages;
    }

    // Getters
    public int getBorrowAdId() {
        return borrowAdId;
    }

    public String getBorrowAdIsbn() {
        return borrowAdIsbn;
    }

    public String getBorrowPublisher() {
        return borrowPublisher;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public String getLocation() {
        return borrowLocation;
    }

    public String getTitle() {
        return bookTitle;
    }

    public String getDescription() {
        return bookDescription;
    }

    public int getPages() {
        return bookPages;
    }
}