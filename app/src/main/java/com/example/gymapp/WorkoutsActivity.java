package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.databinding.ActivityMainBinding;
import com.example.gymapp.sampledata.WorkoutViewModel;

import java.util.HashMap;
import java.util.Map;

public class WorkoutsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private WorkoutViewModel viewModel;
    private static final String TAG = "Workouts";
    private static Button viewWorkoutsButton;
    private static Button newWorkoutButton;
    private static Button supriseButton;
    private static Button backButton;
    private static Button settingsButton;
    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SignUp(Bundle) called");
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.fragment_workouts);
        username = getIntent().getExtras().getString("Username");
        //setSupportActionBar(binding.toolbar);

        backButton = findViewById(R.id.back_button);
        settingsButton = findViewById(R.id.settings_workout_button);
        viewWorkoutsButton = findViewById(R.id.view_workouts_button);
        newWorkoutButton = findViewById((R.id.new_workout_button));
        supriseButton = findViewById(R.id.surprise_button);




        viewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);

        // Example: Add a workout
        Map<String, Object> workout = new HashMap<>();
        workout.put("name", "Push-up");
        workout.put("type", "Strength");
        workout.put("duration", "10 minutes");
        viewModel.addWorkout(workout);


        viewWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyWorkoutsActivity.class);
                i.putExtra("Username", username);
                startActivity(i);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                i.putExtra("Username", username);
                startActivity(i);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //go to settings
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        //EditText usernameEditText = view.findViewById(R.id.username_input);  // Updated ID
        //EditText passwordEditText = view.findViewById(R.id.password_input);  // Updated ID
        //Button signUpButton = view.findViewById(R.id.create_account_button);
        //Button backButton = view.findViewById(R.id.back_button5);

    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}