package com.example.gymapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AllExercisesActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "All Exercises";
    private static Button backButton;
    private static String userDocument;
    private static String exerciseDocument;
    int id;

    ArrayList<Map<String, Object>> exercises = new ArrayList<>();
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    Map<String,Object> exercise;


    public void refreshExerciseList(){
        db.collection("users").document(userDocument).collection("myExercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String,Object> exercise1 = document.getData();
                                exercise1.put("document", document.getId());
                                exercises.add(exercise1);
                                createButton(exercise1, i );
                                i++;
                            }
                            //createButtons();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void createButton(Map<String, Object> exercise1, int i){
        LinearLayout linear = (LinearLayout) findViewById(R.id.layout1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btn = new Button(this);
        btn.setId(i);
        int id_ = btn.getId();
        btn.setText((String) exercise1.get("name"));
        //btn.setBackgroundColor(Color.rgb(70, 80, 90));
        linear.addView(btn, params);
        Button btn1 = ((Button) findViewById(id_));


        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                exerciseDocument = (String) exercise1.get("document");
                Intent i = new Intent(getApplicationContext(), EditExerciseActivity.class);
                i.putExtra("userDocument", userDocument);
                i.putExtra("workoutDocument", exerciseDocument);
                startActivity(i);
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SignUp(Bundle) called");
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.fragment_all_exercises);
        userDocument = getIntent().getExtras().getString("Document");

        //setSupportActionBar(binding.toolbar);

        backButton = findViewById(R.id.back_button9);
        //Button test = findViewById(R.id.button);
        //deleteWorkoutButton = findViewById(R.id.delete_workout_button);
        refreshExerciseList();



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