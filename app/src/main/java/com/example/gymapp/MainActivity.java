package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    private final Map<String, String> users = new HashMap<>();

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//R.id.layout.fragment_first

        setSupportActionBar(binding.toolbar);

        usernameEditText = findViewById(R.id.username_entry);
        passwordEditText = findViewById(R.id.password_entry);
        signInButton = findViewById(R.id.sign_in_button);
        signUpButton = findViewById(R.id.sign_up_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //line below allows sign in with no user or password

                Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
                startActivity(i);
                //logic below requires valid user and password
                /*
               if (users.containsKey(username) && users.get(username).equals(password)) {
                    Toast.makeText(getActivity(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                } else {
                    Toast.makeText(getActivity(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked signUp");
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);

            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


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
        //usernameEditText = view.findViewById(R.id.username_entry);
        //passwordEditText = view.findViewById(R.id.password_entry);
        //Button signInButton = view.findViewById(R.id.sign_in_button);


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