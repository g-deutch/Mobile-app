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

    // Getters and setters
    // ...
}

