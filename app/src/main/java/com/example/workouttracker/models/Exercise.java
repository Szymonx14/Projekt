package com.example.workouttracker.models;

public class Exercise {
    private int id;
    private int workoutId;
    private String name;
    private int sets;
    private int reps;
    private double weight; // w kg

    public Exercise() {}

    public Exercise(int workoutId, String name, int sets, int reps, double weight) {
        this.workoutId = workoutId;
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Gettery
    public int getId() { return id; }
    public int getWorkoutId() { return workoutId; }
    public String getName() { return name; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public double getWeight() { return weight; }

    // Settery
    public void setId(int id) { this.id = id; }
    public void setWorkoutId(int workoutId) { this.workoutId = workoutId; }
    public void setName(String name) { this.name = name; }
    public void setSets(int sets) { this.sets = sets; }
    public void setReps(int reps) { this.reps = reps; }
    public void setWeight(double weight) { this.weight = weight; }
}
