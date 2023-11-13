package com.example.gymapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "Search";
    private static Button backButton;
    private static Button SearchButton;
    private static EditText SearchWord;

    private static String document;
    private static String userDocument;


    private static String exerciseDocument;
    int id;

    ArrayList<Map<String, Object>> exercises = new ArrayList<>();
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    Map<String,Object> exercise;

    public void refreshExerciseList(final String muscle) {
        db.collection("users").document(document).collection("myExercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            exercises.clear(); // Clear existing exercises before refreshing the list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> exerciseData = document.getData();
                                if (exerciseData.containsKey("muscle") && String.valueOf(exerciseData.get("muscle")).toLowerCase().contains(muscle.toLowerCase())) {
                                    exerciseData.put("document", document.getId());
                                    exercises.add(exerciseData);
                                    createButton(exercise, i);
                                    i++;
                                }
                            }
                            if (exercises.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "No exercises found for muscle: " + muscle, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), i + " exercises found for muscle: " + muscle, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting exercises.", task.getException());
                        }
                    }
                });
    }
    public void createButton(Map<String, Object> exercise1, int i) {
        // Get reference to the LinearLayout with ID "line1" in your XML layout
        LinearLayout linear = findViewById(R.id.layout1);

        // Set up layout parameters for the Button
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn = new Button(this);
        btn.setId(i);
        int id_ = btn.getId();
        btn.setText((String) exercise1.get("name"));
        linear.addView(btn, params);
        Button btn1 = ((Button) findViewById(id_));
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SignUp(Bundle) called");
        setContentView(R.layout.fragment_search);
        document = getIntent().getExtras().getString("Document");

        backButton = findViewById(R.id.back_button3);
        SearchButton = findViewById(R.id.search_button);
        SearchWord = findViewById(R.id.textInputEditText);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText( getApplicationContext(), "Word: " + SearchWord.getText().toString(), Toast.LENGTH_SHORT).show();
                String muscleToSearch = SearchWord.getText().toString();
                refreshExerciseList(muscleToSearch);
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