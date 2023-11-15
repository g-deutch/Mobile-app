package com.example.gymapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.R;
import com.example.gymapp.adapter.ExerciseAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.Exercise;
import model.Workout;

public class ExerciseSelectionActivity extends AppCompatActivity {
    private RecyclerView recyclerViewExercises;
    private ExerciseAdapter exerciseAdapter;
    private Button buttonSaveWorkout, buttonNextDay, buttonPreviousDay;
    private TextView textViewCurrentDay;
    private String document;
    private int currentDayNumber = 1; // Start from day 1
    private int totalWorkoutDays; // Total number of workout days
    private String workoutDayType;
    private EditText editTextWorkoutName;
    List<String> documents = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);
        document = getIntent().getExtras().getString("Document");

        editTextWorkoutName = findViewById(R.id.editTextWorkoutName);

        //recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        //buttonSaveWorkout = findViewById(R.id.buttonSaveWorkout);

        // Initialize RecyclerView
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(new ArrayList<>());
        recyclerViewExercises.setAdapter(exerciseAdapter);

        // Initialize other UI components
        buttonSaveWorkout = findViewById(R.id.buttonSaveWorkout);
        buttonNextDay = findViewById(R.id.buttonNextDay);
        buttonPreviousDay = findViewById(R.id.buttonPreviousDay);
        textViewCurrentDay = findViewById(R.id.textViewCurrentDay);

        totalWorkoutDays = getIntent().getIntExtra("TOTAL_WORKOUT_DAYS", 3); // Default to 3 days if not specified

        updateDayAndFetchExercises();

        // Handle save button click
        buttonSaveWorkout.setOnClickListener(view -> saveWorkoutPlan());

        // Handle next and previous day button clicks
        buttonNextDay.setOnClickListener(view -> {
            if (currentDayNumber < totalWorkoutDays) {
                currentDayNumber++;
                updateDayAndFetchExercises();
            }
        });

        buttonPreviousDay.setOnClickListener(view -> {
            if (currentDayNumber > 1) {
                currentDayNumber--;
                updateDayAndFetchExercises();
            }
        });
    }

    private void updateDayAndFetchExercises() {
        workoutDayType = determineWorkoutDayType(currentDayNumber);
        textViewCurrentDay.setText("Current Day: " + workoutDayType);
        fetchExercises(workoutDayType);
    }

    private String determineWorkoutDayType(int dayNumber) {
        String[] cycle = {"Push", "Pull", "Legs"};
        return cycle[(dayNumber - 1) % cycle.length];
    }

    private void fetchExercises(String workoutSplit) {
        // Logic to fetch exercises based on the workout split
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<String> muscles = new ArrayList<>();
        switch (workoutDayType) {
            case "Push":
                muscles = Arrays.asList("Chest", "Shoulders", "Triceps");
                break;
            case "Pull":
                muscles = Arrays.asList("Biceps", "Forearms", "Middle Back", "Lower Back", "Lats Back");
                break;
            case "Legs":
                muscles = Arrays.asList("Quadriceps", "Hamstrings", "Calves", "Glutes");
                break;
        }
        documents.clear();

        db.collection("users").document(document).collection("myExercises")
                .whereIn("muscle", muscles)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Log.d("ExerciseSelection", "No exercises found for muscle group: " + workoutDayType);
                    } else {
                        for (DocumentSnapshot document:queryDocumentSnapshots
                             ) {
                            documents.add(document.getId());
                        }
                        List<Exercise> exercises = queryDocumentSnapshots.toObjects(Exercise.class);
                        int i = 0;
                        for (Exercise exercise:exercises
                             ) {
                            exercise.setDocument(documents.get(i));
                            i++;
                        }
                        exerciseAdapter.updateExercises(exercises);
                        Log.d("ExerciseSelection", "Exercises fetched: " + exercises.size());
                        for (Exercise exercise : exercises) {
                            Log.d("ExerciseSelection", "Fetched: " + exercise.getMuscle() + ", " + exercise.getPreviewSrc());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ExerciseSelection", "Error fetching exercises", e);
                });
    }

    private void saveWorkoutPlan() {
        String workoutName = editTextWorkoutName.getText().toString().trim();

        // Validate the workout name
        if (workoutName.isEmpty()) {
            Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<Exercise> selectedExercises = exerciseAdapter.getSelectedExercises();
        // Logic to save or pass the workout plan
        if (selectedExercises.isEmpty()) {
            Toast.makeText(this, "Please select at least one exercise", Toast.LENGTH_SHORT).show();
            return;
        }
        Workout workout = new Workout(workoutName, new ArrayList<>(selectedExercises), new Date());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserDocId = getCurrentUserDocId(); // Method to get the current user's Firestore document I


        if (currentUserDocId != null) {

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
