package com.example.gymapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.R;
import com.example.gymapp.adapter.ExerciseAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.Exercise;
import model.Workout;

public class ExerciseSelectionActivity extends AppCompatActivity {
    private RecyclerView recyclerViewExercises;
    private ExerciseAdapter exerciseAdapter;
    private Button buttonSaveWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        buttonSaveWorkout = findViewById(R.id.buttonSaveWorkout);

        // Initialize RecyclerView
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(new ArrayList<>());
        recyclerViewExercises.setAdapter(exerciseAdapter);

        // Get the passed workout split data
        String workoutSplit = getIntent().getStringExtra("WORKOUT_SPLIT");

        // Fetch exercises based on workout split
        fetchExercises(workoutSplit);

        // Handle save button click
        buttonSaveWorkout.setOnClickListener(view -> saveWorkoutPlan());
    }
    private void fetchExercises(String workoutSplit) {
        // Logic to fetch exercises based on the workout split
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("exercises")
                .whereEqualTo("muscle", "Chest")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Log.d("ExerciseSelection", "No exercises found for muscle group: " + workoutSplit);
                    } else {
                        List<Exercise> exercises = queryDocumentSnapshots.toObjects(Exercise.class);
                        exerciseAdapter.updateExercises(exercises);
                        Log.d("ExerciseSelection", "Exercises fetched: " + exercises.size());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ExerciseSelection", "Error fetching exercises", e);
                });
    }

    private void saveWorkoutPlan() {
        Set<Exercise> selectedExercises = exerciseAdapter.getSelectedExercises();
        // Logic to save or pass the workout plan
        if (selectedExercises.isEmpty()) {
            Toast.makeText(this, "Please select at least one exercise", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserDocId = getCurrentUserDocId(); // Method to get the current user's Firestore document I


        if (currentUserDocId != null) {
            Workout workout = new Workout("My Custom Workout", new ArrayList<>(selectedExercises), new Date());

            db.collection("users").document(currentUserDocId).collection("myWorkouts")
                    .add(workout)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Workout saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error saving workout", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        } else {
            Toast.makeText(this, "User not identified", Toast.LENGTH_SHORT).show();
        }

    }
    private String getCurrentUserDocId() {
        SharedPreferences sharedPref = getSharedPreferences("YourAppPreference", Context.MODE_PRIVATE);
        String docId = sharedPref.getString("currentUserDocId", null);
        Log.d("ExerciseSelection", "Retrieved user doc ID: " + docId);
        return docId;
    }
}
