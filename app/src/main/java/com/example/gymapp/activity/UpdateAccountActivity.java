package com.example.gymapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.R;
import com.example.gymapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UpdateAccountActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "UpdateAccount";
    private static Button backButton;
    private static Button updateAccount;
    private static EditText newUsername;
    private static EditText newPassword;
    private static String document;
    ArrayList<Map<String, Object>> userList = new ArrayList<>();
    private static Map<String, Object> user = new HashMap<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private void updateUser(String username, String password) {
        document = getIntent().getExtras().getString("Document");
        DocumentReference curr = db.collection("users").document(document);


        boolean changed = false;
        if(!username.equals("")){
            if(!password.equals("")){

                user.put("password", password);
                user.put("username", username);
                curr.update(user);

                changed = true;
            }
        }
        if(changed) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
        }
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
                                Map<String,Object> user1 = document.getData();
                                user1.put("document", document.getId());
                                userList.add(user1);


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
        setContentView(R.layout.fragment_update_account);

        backButton = findViewById(R.id.back_button7);
        updateAccount = findViewById(R.id.update_button);
        newUsername = findViewById((R.id.new_username_entry));
        newPassword = findViewById(R.id.new_password_entry);

        refreshUserList();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(newUsername.getText().toString(), newPassword.getText().toString());
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