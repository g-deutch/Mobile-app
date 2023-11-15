package com.example.gymapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.node.ObserverNodeKt;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class EditExerciseActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "Edit Exercise";
    private static Button backButton;
    private static TextView name;
    private static TextView level;
    private static TextView equipment;
    private static TextView muscle;
    private static Switch active;
    private static Switch favorite;
    private static ImageView imageEx;
    //private static VideoView videoEx;

    private static String userDocument;
    private static String workoutDocument;
    Map<String, Object> exercise = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();





    public void getWorkout(){

        db.collection("users").document(userDocument).collection("myExercises").document(workoutDocument).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete( Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                                exercise = doc.getData();
                                setTextFields();
                                setSwitches();
                                Glide
                                        .with(getApplicationContext()) // replace with 'this' if it's in activity
                                        .load((String) exercise.get("previewSrc"))

                                        .into(imageEx);
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
    public void setTextFields(){
        name.setText((String) exercise.get("name"));
        level.setText("Level: " + (String) exercise.get("level"));
        equipment.setText("Equipment: " + exercise.get("equipment"));
        muscle.setText("Muscle: " + exercise.get("muscle"));

    }
    public void setSwitches(){
        if((boolean) exercise.get("active")){
            active.setChecked(true);
        }
        if((boolean) exercise.get("favorite")){
            active.setChecked(true);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Setting(Bundle) called");
        setContentView(R.layout.fragment_edit_excercise);


        userDocument = getIntent().getExtras().getString("userDocument");
        workoutDocument = getIntent().getExtras().getString("workoutDocument");

        backButton = findViewById(R.id.back_button6);
        name = findViewById(R.id.exerciseName);
        level = findViewById(R.id.level_text);
        equipment = findViewById(R.id.equipment_text);
        muscle = findViewById(R.id.muscle_text);

        active = findViewById(R.id.active_switch);
        favorite = findViewById(R.id.favorite_switch);
        imageEx = findViewById(R.id.workout_image);
       // videoEx = findViewById(R.id.videoView);




        getWorkout();






        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String, Object> switchChange = new HashMap<>();
                switchChange.put("active", isChecked);
                db.collection("users").document(userDocument).collection("myExercises").document(workoutDocument).update(switchChange);
            }
        });
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String, Object> switchChange = new HashMap<>();
                switchChange.put("favorite", isChecked);
                db.collection("users").document(userDocument).collection("myExercises").document(workoutDocument).update(switchChange);
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