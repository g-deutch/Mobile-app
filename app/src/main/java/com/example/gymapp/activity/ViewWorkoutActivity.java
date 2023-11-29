package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewWorkoutActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "View Workout";
    private static Button backButton;
    private static TextView name;



    private static Switch favorite;


    private static String userDocument;
    private static String workoutDocument;
    Map<String, Object> workout = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();





    public void getWorkout(){

        db.collection("users").document(userDocument).collection("myWorkouts").document(workoutDocument).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete( Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                                workout = doc.getData();
                                int i = 0;
                                for (Map<String, Object> exercise:  (ArrayList<Map <String, Object>>) workout.get("exercises")
                                     ) {
                                    createButton(exercise, i);
                                    i++;
                                }
                                setTextFields();
                                //setSwitches();

                                //imageEx.setImageURI(Uri.parse((String) exercise.get("previewSrc")));
                                //videoEx.setVideoURI(Uri.parse((String) exercise.get("previewSrc")));
                            } else {
                                Log.d(TAG, "No such document");
                            }

                        } else {
                            Log.w(TAG, "Error getting exercise.", task.getException());
                        }
                    }
                });

    }
    public void createButton(Map<String, Object> exercise1, int i){
        LinearLayout linear = (LinearLayout) findViewById(R.id.layout4);
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

                String exerciseDocument = (String) exercise1.get("document");
                Intent i = new Intent(getApplicationContext(), EditExerciseActivity.class);
                i.putExtra("userDocument", userDocument);
                i.putExtra("workoutDocument", exerciseDocument);
                startActivity(i);
            }
        });


    }
    public void setTextFields(){
        name.setText((String) workout.get("name"));


    }
    /*
    public void setSwitches(){
        if((boolean) exercise.get("active")){
            active.setChecked(true);
        }
        if((boolean) exercise.get("favorite")){
            active.setChecked(true);
        }
    }

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Setting(Bundle) called");
        setContentView(R.layout.fragment_workout_viewer);


        userDocument = getIntent().getExtras().getString("userDocument");
        workoutDocument = getIntent().getExtras().getString("workoutDocument");

        backButton = findViewById(R.id.back_button12);
        name = findViewById(R.id.workout_name);



       // videoEx = findViewById(R.id.videoView);




        getWorkout();






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