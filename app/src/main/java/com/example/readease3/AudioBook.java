package com.example.readease3;

public class AudioBook {
    private int id;
    private String isbn;
    private String language;
    private String title;
    private int pages;
    private String date;
    private double price;

    public AudioBook(int id, String isbn, String language, String title, int pages, String date, double price) {
        this.id = id;
        this.isbn = isbn;
        this.language = language;
        this.title = title;
        this.pages = pages;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
