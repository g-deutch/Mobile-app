package model;

import java.util.Date;
import java.util.List;

public class Workout {
    private String name;
    private List<Exercise> exercises;
    private Date dateCreated;

    public Workout() {
        // Default constructor required for Firestore
    }

    public Workout(String name, List<Exercise> exercises, Date dateCreated) {
        this.name = name;
        this.exercises = exercises;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}

