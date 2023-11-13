package com.example.gymapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import model.Workout;

public class PushActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "push";
    private static Button backButton;
    private static String documentId;
    private TextView textViewName;
    private TextView textViewDuration;
    private TextView textViewType;
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    public class Workout {
        private String name, type, duration;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SignUp(Bundle) called");
        setContentView(R.layout.fragment_push);
        backButton = findViewById(R.id.back_button4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        textViewName = findViewById(R.id.textViewName);
        textViewDuration = findViewById(R.id.textViewDuration);
        textViewType = findViewById(R.id.textViewType);

        // document ID
        documentId = "2hT6UCN3kfGZM8bw9Q8H";
        getWorkout(documentId);
    }

    private void getWorkout(String documentId) {
        //String name = db.collection("workouts").document(documentId).collection("name");

        db.collection("workouts").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Toast.makeText(this, "Workout  found", Toast.LENGTH_SHORT).show();
                        Map<String, Object> data = documentSnapshot.getData();
                        Workout w = new Workout();
                        w.setName((String) data.get("name"));
                        w.setType((String) data.get("type"));
                        w.setDuration((String) data.get("duration"));
                        displayWorkout(w);
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show();
                });
    }
    private void displayWorkout(Workout workout) {
        textViewName.setText("Name: " + workout.getName());
        textViewDuration.setText("Duration: " + workout.getDuration());
        textViewType.setText("Type: " + workout.getType());
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