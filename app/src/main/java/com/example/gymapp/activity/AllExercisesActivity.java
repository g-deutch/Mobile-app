package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.R;
import com.example.gymapp.adapter.AllExercisesAdapter;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AllExercisesActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "All Exercises";
    private static Button backButton;
    public static String userDocument;
    private RecyclerView recyclerView;
    private AllExercisesAdapter  adapter;
    private static String exerciseDocument;
    int id;

    ArrayList<Map<String, Object>> exercises = new ArrayList<>();
    FirebaseFirestore db  = FirebaseFirestore.getInstance();

    private static final int PAGE_SIZE = 10;
    private DocumentSnapshot lastVisible;
    private boolean isLoading = false;
    Map<String,Object> exercise;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_exercises);
        userDocument = getIntent().getExtras().getString("Document");
        Log.d(TAG, "User Document ID: " + userDocument); // Log statement to validate userDocument


        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AllExercisesAdapter(this, exercises, userDocument);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshExerciseList();

        Button backButton = findViewById(R.id.back_button9);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void refreshExerciseList() {
        if (isLoading) return;
        isLoading = true;

        Query query =
        db.collection("users").document(userDocument).collection("myExercises");
                //.get();// Simpler query without orderBy
                //.orderBy("timestamp") // Ensure there's an index on this field
                //.limit(PAGE_SIZE);

        if (lastVisible != null) {
            query = query.startAfter(lastVisible);
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                isLoading = false;
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    Log.d(TAG, "QuerySnapshot Size: " + querySnapshot.size());

                    Log.w(TAG, "getting documents.", task.getException());
                    boolean qs = querySnapshot.isEmpty();
                    if (!(querySnapshot.isEmpty())) {
                        lastVisible = querySnapshot.getDocuments().get(querySnapshot.size() - 1);
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> exercise = document.getData();
                            exercise.put("document", document.getId());
                            exercises.add(exercise);
                        }
                        Log.d(TAG, "Number of exercises fetched: " + exercises.size());
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "No documents found");

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
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