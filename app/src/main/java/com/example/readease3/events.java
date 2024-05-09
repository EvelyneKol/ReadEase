package com.example.readease3;

public class events {
    private int eventId;
    private String title;
    private String description;
    private String location;
    private int capacity;
    private int creator;
    private String dateTime;

    public events(int eventId, String title, String description, String dateTime, String location, int capacity, int creator) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.creator = creator;
    }

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId){this.eventId = eventId;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){ this.description = description; }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getCreator() {
        return creator;
    }
    public void setCreator(int creator){
        this.creator = creator;
    }


}
