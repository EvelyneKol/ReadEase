package com.example.readease3;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String description;
    private int pages;
    private String category;

    // Constructor
    public Book(String isbn, String title, String author, String description, int pages, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.pages = pages;
        this.category = category;
    }

    // Getters and setters (you can generate them automatically in most IDEs)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
