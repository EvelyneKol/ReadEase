package com.example.readease3;

public class events {
    private int eventId;
    private String title;
    private String description;
    private int creator;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private int capacity;

    public events(int eventId, String title, String description, String date,String startTime, String endTime, String location, int capacity, int creator) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.endTime=endTime;
        this.startTime=startTime;
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

    public String getDate() {
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
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
