package com.example.workouttracker.models;

public class Workout {
    private int id;
    private String name;
    private String type;
    private String date;
    private int duration; // w minutach
    private String notes;

    public Workout() {
    }

    public Workout(String name, String type, String date, int duration, String notes) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.notes = notes;
    }

    // Gettery
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public String getNotes() {
        return notes;
    }

    // Settery
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
