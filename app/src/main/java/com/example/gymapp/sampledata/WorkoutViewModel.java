package com.example.gymapp.sampledata;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class WorkoutViewModel extends ViewModel {

    private WorkoutRepository repository;

    public WorkoutViewModel() {
        repository = new WorkoutRepository();
    }

    public Task<Void> addWorkout(Map<String, Object> workout) {
        return repository.addWorkout(workout);
    }

    public Task<QuerySnapshot> getAllWorkouts() {
        return repository.getAllWorkouts();
    }

    public Task<Void> updateWorkout(String documentId, Map<String, Object> updatedData) {
        return repository.updateWorkout(documentId, updatedData);
    }

    public Task<Void> deleteWorkout(String documentId) {
        return repository.deleteWorkout(documentId);
    }
}

