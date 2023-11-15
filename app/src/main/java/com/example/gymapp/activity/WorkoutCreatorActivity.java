package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.R;


public class WorkoutCreatorActivity extends AppCompatActivity {

    private Spinner spinnerWorkoutDays;
    private RadioGroup radioGroupSplits;
    private Button buttonToExerciseSelection;
    private String selectedWorkoutDay;
    private final String selectedSplit = "Push/Pull/Legs"; // Set the split to PPL by default

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Respond to the action bar's Up/Home button
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize UI components
        spinnerWorkoutDays = findViewById(R.id.spinnerWorkoutDays);
        buttonToExerciseSelection = findViewById(R.id.buttonToExerciseSelection);

        // Set up the components
        setupSpinner();
        setupButton();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workout_days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkoutDays.setAdapter(adapter);

        spinnerWorkoutDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWorkoutDay = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWorkoutDay = null;
            }
        });
    }

    private void setupButton() {
        buttonToExerciseSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWorkoutDay != null) {
                    int dayNumber = Integer.parseInt(selectedWorkoutDay); // Assuming selectedWorkoutDay is the day number as a string
                    String workoutDayType = determineWorkoutDayType(dayNumber);

                    Intent intent = new Intent(WorkoutCreatorActivity.this, ExerciseSelectionActivity.class);
                    intent.putExtra("WORKOUT_DAY", workoutDayType);
                    startActivity(intent);
                } else {
                    Toast.makeText(WorkoutCreatorActivity.this, "Please select workout days", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private String determineWorkoutDayType(int dayNumber) {
        String[] cycle = {"Push", "Pull", "Legs"};
        return cycle[(dayNumber - 1) % cycle.length]; // Determine the workout type based on the day number
    }

}

