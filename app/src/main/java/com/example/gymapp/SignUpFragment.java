package com.example.gymapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymapp.databinding.FragmentMyWorkoutsBinding;
import com.example.gymapp.databinding.FragmentSecondBinding;
import com.example.gymapp.databinding.FragmentSettingsBinding;
import com.example.gymapp.databinding.FragmentSignUpBinding;
import com.example.gymapp.databinding.FragmentWorkoutsBinding;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;




public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private static final Map<String, String> users = new HashMap<>();
    String username;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find UI elements by their IDs
        EditText usernameEditText = view.findViewById(R.id.username_input);  // Updated ID
        EditText passwordEditText = view.findViewById(R.id.password_input);  // Updated ID
        Button signUpButton = view.findViewById(R.id.create_account_button);
        Button backButton = view.findViewById(R.id.back_button5);
        /*
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if(!password.equals("")) {
                    if (users.containsKey(username)) {
                        Toast.makeText(getActivity(), "Username already taken", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                        users.put(username, password);

                        NavHostFragment.findNavController(SignUpFragment.this)
                                .navigate(R.id.action_SignUpFragment_to_FirstFragment);
                        backButton.performClick();

                    }
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                } else {
                    Toast.makeText(getActivity(), "Must input password", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */
    }

/*
    private FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_SignUpFragment_to_FirstFragment);
            }
        });
    } */
}