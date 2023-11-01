package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;




public class SettingsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "Settings";
    private static Button backButton;
    private static Button deleteWorkoutButton;
    private static String document;
    ArrayList<Map<String, Object>> userList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void delete() {
//        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (currUser != null) {
//            String userId = currUser.getUid();
//
//            db.collection("users").document(userId).delete()
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(getApplicationContext(), "deleted successfully.", Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
//                    });
//        } else {
//            Toast.makeText(getApplicationContext(), "No authenticated user.", Toast.LENGTH_SHORT).show();
//        }


//        //removes from userList
//        String usernameToRemove = getIntent().getExtras().getString("Username");
//        for (int i = 0; i < userList.size(); i++) {
//            Map<String, Object> user = userList.get(i);
//            String curr = (String) user.get("username");
//
//            if (curr.equals(usernameToRemove)) {
//                userList.remove(i);
//                i--;  // Decrement 'i' to adjust for the removed element
//              //  Toast.makeText(getApplicationContext(), "success: " + curr, Toast.LENGTH_SHORT).show();
//            }
//        }


        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document1 : task.getResult()) {
                    document = getIntent().getExtras().getString("Document");
                    db.collection("users").document(document).delete();
                    Toast.makeText(getApplicationContext(), "Delete success: " + document, Toast.LENGTH_SHORT).show();

                    // String usernameToRemove = getIntent().getExtras().getString("Username");
//                    for (int i = 0; i < userList.size(); i++) {
//                        Map<String, Object> user = userList.get(i);
//                        String curr = (String) user.get("username");
//                        if (curr.equals(usernameToRemove)) {
//                            userList.remove(i);
//                            db.collection("users").document(document.getId()).delete();
//                            i--;
//                            Toast.makeText(getApplicationContext(), "Delete success: " + document.getId(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }
            }
        });
    }

    public void refreshUserList(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                userList.add(document.getData());

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Setting(Bundle) called");
        setContentView(R.layout.fragment_settings);

        backButton = findViewById(R.id.back_button4);
        deleteWorkoutButton = findViewById(R.id.delete_account_button);

        refreshUserList();
        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                setContentView(R.layout.activity_main);
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