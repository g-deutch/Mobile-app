package com.example.gymapp.sampledata;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class WorkoutRepository {

    private FirebaseFirestore db;

    public WorkoutRepository() {
        db = FirebaseFirestore.getInstance();
    }

    // Create/Add a new workout
    public Task<Void> addWorkout(Map<String, Object> workout) {
        return db.collection("workouts").document().set(workout);
    }

    // Read/Fetch all workouts
    public Task<QuerySnapshot> getAllWorkouts() {
        return db.collection("workouts").get();
    }

    // Update a workout
    public Task<Void> updateWorkout(String documentId, Map<String, Object> updatedData) {
        return db.collection("workouts").document(documentId).update(updatedData);
    }

    // Delete a workout
    public Task<Void> deleteWorkout(String documentId) {
        return db.collection("workouts").document(documentId).delete();
    }
}

