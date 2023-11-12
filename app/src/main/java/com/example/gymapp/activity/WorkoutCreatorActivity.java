package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
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
    private String selectedSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);

        // Initialize UI components
        spinnerWorkoutDays = findViewById(R.id.spinnerWorkoutDays);
        radioGroupSplits = findViewById(R.id.radioGroupSplits);
        buttonToExerciseSelection = findViewById(R.id.buttonToExerciseSelection);

        // Set up the components
        setupSpinner();
        setupRadioGroup();
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

    private void setupRadioGroup() {
        radioGroupSplits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonFullBody) {
                    selectedSplit = "Full Body";
                } else if (checkedId == R.id.radioButtonUpperLower) {
                    selectedSplit = "Upper/Lower";
                } else if (checkedId == R.id.radioButtonPushPullLegs) {
                    selectedSplit = "Push/Pull/Legs";
                } else {
                    selectedSplit = null;
                }
            }

        });
    }

    private void setupButton() {
        buttonToExerciseSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWorkoutDay != null && selectedSplit != null) {
                    Intent intent = new Intent(WorkoutCreatorActivity.this, ExerciseSelectionActivity.class);
                    intent.putExtra("WORKOUT_DAY", selectedWorkoutDay);
                    intent.putExtra("WORKOUT_SPLIT", selectedSplit);
                    startActivity(intent);
                } else {
                    Toast.makeText(WorkoutCreatorActivity.this, "Please select both workout day and split", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

